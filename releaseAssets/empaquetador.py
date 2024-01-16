#!/usr/bin/env python3
import os
from pathlib import Path
import shutil


VERSION_ACTUAL = "1.1-snapshot"

RUTA_RELASSETS = Path(os.path.realpath(__file__)).parent
RUTA_REPO = Path(os.path.realpath(__file__)).parent.parent
RUTA_TARGET = Path(RUTA_REPO, "target")
RUTA_ARTEFACTO = Path(
    RUTA_TARGET, f"creadorManual-{VERSION_ACTUAL}-jar-with-dependencies.jar"
)

if not RUTA_TARGET.exists:
    print(RUTA_TARGET, "no existe, por lo que aún no se ha construido el proyecto")
    exit(1)
elif not RUTA_ARTEFACTO.exists:
    print("El artefacto", RUTA_ARTEFACTO, "no existe!")
    exit(1)

RUTA_PAQUETES = Path(RUTA_TARGET, "paquetes/")
if RUTA_PAQUETES.exists():
    shutil.rmtree(RUTA_PAQUETES)
RUTA_PAQUETES.mkdir(parents=True)

RUTA_PAQ_WIN = Path(RUTA_PAQUETES, "windows")
RUTA_PAQ_LIN = Path(RUTA_PAQUETES, "linux")
RUTA_PAQ_WIN.mkdir()
RUTA_PAQ_LIN.mkdir()

ruta_plantilla = Path(RUTA_RELASSETS, "plantilla.xlsx")
shutil.copy(ruta_plantilla, Path(RUTA_PAQ_WIN, "plantilla.xlsx"))
shutil.copy(ruta_plantilla, Path(RUTA_PAQ_LIN, "plantilla.xlsx"))

ruta_formato_gpt = Path(RUTA_RELASSETS, "formato_prompt.txt")
shutil.copy(ruta_formato_gpt, Path(RUTA_PAQ_WIN, "formato_prompt.txt"))
shutil.copy(ruta_formato_gpt, Path(RUTA_PAQ_LIN, "formato_prompt.txt"))

RUTA_BIN_WIN = Path(RUTA_PAQ_WIN, "binarios")
RUTA_BIN_LIN = Path(RUTA_PAQ_LIN, "binarios")
RUTA_BIN_WIN.mkdir()
RUTA_BIN_LIN.mkdir()

ruta_capturador = Path(RUTA_RELASSETS, "windows", "silicon.exe")
shutil.copy2(ruta_capturador, Path(RUTA_BIN_WIN, "silicon.exe"))
ruta_capturador = Path(RUTA_RELASSETS, "linux", "silicon")
shutil.copy2(ruta_capturador, Path(RUTA_BIN_LIN, "silicon"))
ruta_capturador = Path(RUTA_RELASSETS, "linux", "germanium")
shutil.copy2(ruta_capturador, Path(RUTA_BIN_LIN, "germanium"))

shutil.copy(RUTA_ARTEFACTO, Path(RUTA_PAQ_WIN, f"creadorManual-{VERSION_ACTUAL}.jar"))
shutil.copy(RUTA_ARTEFACTO, Path(RUTA_PAQ_LIN, f"creadorManual-{VERSION_ACTUAL}.jar"))

launcher_windows = open(str(Path(RUTA_PAQ_WIN, "creadorManual.bat")), "x")
launcher_windows.write(f"java -jar creadorManual-{VERSION_ACTUAL}.jar")
launcher_windows.close()

shutil.make_archive(
    str(Path(RUTA_PAQUETES, f"windows-creadorManual-{VERSION_ACTUAL}")),
    "zip",
    RUTA_PAQ_WIN,
)
shutil.make_archive(
    str(Path(RUTA_PAQUETES, f"linux-creadorManual-{VERSION_ACTUAL}")),
    "gztar",
    RUTA_PAQ_WIN,
)
