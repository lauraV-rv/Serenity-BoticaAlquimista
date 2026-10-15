#!/usr/bin/env bash
# ---------------------------------------------------------------------------
# Publica el resultado de cada escenario ejecutado en su issue (TestCase).
#
#  - Lee el cucumber.json que genera el Runner.
#  - Por cada escenario con tag @Issue-N busca el issue cuyo titulo empieza
#    por "[@Issue-N]" (asi lo crea crear-issues.ps1) y:
#      * comenta el resultado (paso / fallo, fecha y enlace a la ejecucion),
#      * le pone la etiqueta status:passed o status:failed.
#  - Escribe una tabla "Resultados por issue" en el resumen del workflow.
#  - Falla si el filtro de tags no ejecuto ningun escenario (tag mal escrito).
#
# Uso:  publicar-resultados.sh <cucumber.json> [--solo-resumen]
# Variables: GITHUB_REPOSITORY, URL_EJECUCION, GH_TOKEN (las pone el workflow).
# ---------------------------------------------------------------------------
set -euo pipefail

REPORTE="${1:-target/cucumber-reports/cucumber.json}"
MODO="${2:-}"
REPO="${GITHUB_REPOSITORY:?Falta GITHUB_REPOSITORY}"
URL="${URL_EJECUCION:-}"
RESUMEN="${GITHUB_STEP_SUMMARY:-/dev/null}"
FECHA="$(TZ=America/Bogota date '+%Y-%m-%d %H:%M') (hora Colombia)"
TMP="$(mktemp -d)"

if [ ! -s "$REPORTE" ]; then
  echo "::error::No se genero $REPORTE: la suite no llego a ejecutar escenarios."
  exit 1
fi

# Una linea por escenario ejecutado:  tag <TAB> nombre <TAB> passed|failed
jq -r '
  .[] | .elements[]?
  | select(.type == "scenario")
  | select(any(.steps[]?; .result.status != "skipped"))
  | . as $e
  | ([.tags[]?.name | select(test("^@Issue-[0-9]+$"))] | first) as $tag
  | select($tag != null)
  | [ $tag,
      $e.name,
      (if any(($e.before // [])[], ($e.steps // [])[], ($e.after // [])[];
              .result.status as $s | ["failed","undefined","pending","ambiguous"] | index($s))
       then "failed" else "passed" end) ]
  | @tsv' "$REPORTE" > "$TMP/resultados.tsv"

TOTAL=$(wc -l < "$TMP/resultados.tsv" | tr -d ' ')
FALLIDOS=$(awk -F'\t' '$3=="failed"' "$TMP/resultados.tsv" | wc -l | tr -d ' ')
EXITOSOS=$((TOTAL - FALLIDOS))

{
  echo "### Resultados por issue"
  echo ""
  echo "Escenarios ejecutados: **$TOTAL** · ✅ $EXITOSOS · ❌ $FALLIDOS"
  echo ""
  echo "| Tag | Issue | Escenario | Resultado |"
  echo "|---|---|---|---|"
} >> "$RESUMEN"

if [ "$TOTAL" -eq 0 ]; then
  echo "::error::El filtro de tags no selecciono ningun escenario. Revisa el tag (ej. @Issue-1, @req_login, @HU-01)."
  exit 1
fi

# Mapa  [@Issue-N] -> numero real del issue en GitHub
gh issue list --repo "$REPO" --state all --limit 500 --json number,title > "$TMP/issues.json" || echo "[]" > "$TMP/issues.json"

if [ "$MODO" != "--solo-resumen" ]; then
  gh label create status:passed --repo "$REPO" --color 0E8A16 \
    --description "El escenario paso en la ultima ejecucion del pipeline" --force > /dev/null
  gh label create status:failed --repo "$REPO" --color D73A4A \
    --description "El escenario fallo en la ultima ejecucion del pipeline" --force > /dev/null
fi

while IFS=$'\t' read -r TAG NOMBRE ESTADO; do
  NUMERO=$(jq -r --arg p "[$TAG]" 'map(select(.title | startswith($p))) | first | .number // empty' "$TMP/issues.json")

  if [ "$ESTADO" = "passed" ]; then
    ICONO="✅ Paso"; PONER="status:passed"; QUITAR="status:failed"
  else
    ICONO="❌ Fallo"; PONER="status:failed"; QUITAR="status:passed"
  fi

  if [ -z "$NUMERO" ]; then
    echo "| \`$TAG\` | (sin issue) | $NOMBRE | $ICONO |" >> "$RESUMEN"
    echo "Aviso: no hay issue con titulo [$TAG]"
    continue
  fi

  echo "| \`$TAG\` | #$NUMERO | $NOMBRE | $ICONO |" >> "$RESUMEN"
  echo "$TAG -> issue #$NUMERO: $ICONO"

  if [ "$MODO" != "--solo-resumen" ]; then
    gh issue comment "$NUMERO" --repo "$REPO" --body "**Resultado de la ejecucion automatizada: $ICONO**

| Campo | Valor |
|---|---|
| Escenario | $NOMBRE |
| Tag | \`$TAG\` |
| Fecha | $FECHA |
| Ejecucion | $URL |

El reporte de Serenity (pasos y capturas) esta en los artefactos de la ejecucion." > /dev/null
    gh issue edit "$NUMERO" --repo "$REPO" --add-label "$PONER" --remove-label "$QUITAR" > /dev/null 2>&1 \
      || gh issue edit "$NUMERO" --repo "$REPO" --add-label "$PONER" > /dev/null
  fi
done < "$TMP/resultados.tsv"

rm -rf "$TMP"
