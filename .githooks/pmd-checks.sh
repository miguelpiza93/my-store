#!/bin/bash

# Directorio temporal para almacenar archivos modificados
TEMP_DIR="pmd_temp"
PMD_RULESET="pmd/rulesets/basic-rulesets-java.xml"
PMD_REPORT="target/pmd/pmd.csv"

# Crear el directorio temporal si no existe
mkdir -p "$TEMP_DIR"

# Obtener los archivos Java modificados (en el área de staging)
modified_files=$(git diff --cached --name-only --diff-filter=ACM | grep '\.java$')

# Verificar si hay archivos modificados
if [ -z "$modified_files" ]; then
    echo "No modified Java files to check."
    exit 0
fi

# Copiar los archivos modificados al directorio temporal
for file in $modified_files; do
    if [ -f "$file" ]; then
        mkdir -p "$TEMP_DIR/$(dirname "$file")"
        cp "$file" "$TEMP_DIR/$file"
    fi
done

# Ejecutar PMD solo sobre los archivos modificados en el directorio temporal
mvn pmd:check -DsourceDirectory="$TEMP_DIR" -Dpmd.rulesets="$PMD_RULESET"

# Capturar el resultado de PMD
pmd_exit_code=$?

if [ $pmd_exit_code -ne 0 ]; then
    echo "PMD violations found:"
    if [ -f "$PMD_REPORT" ]; then
        cat "$PMD_REPORT"
    else
        echo "No PMD report found."
    fi
    rm -rf "$TEMP_DIR"
    exit 1
else
    echo "No PMD violations found."
    rm -rf "$TEMP_DIR"
    exit 0
fi
