FINAL_NAME=SYM_Labo2_Meier_Milani_Palumbo


all: ${FINAL_NAME}.pdf

${FINAL_NAME}.pdf: report.md
	pandoc --pdf-engine=xelatex --top-level-division=section -o ${FINAL_NAME}.pdf report.md

