#!/usr/bin/env python3
import os
from pathlib import Path
import shutil
from enum import Enum


class Versiones(Enum):
    SNAPSHOT = "snapshot"
    BETA = "beta"
    RELEASE = ""


NUMERO_VERSION = "1.2.0"
TIPO_VERSION = Versiones.RELEASE


def obtener_version(version, tipo_version: Versiones):
    if tipo_version == Versiones.RELEASE:
        return version
    else:
        return f"{version}-{tipo_version.value}"


def obtener_version_empaquetado(version, tipo_version: Versiones):
    if tipo_version == Versiones.RELEASE:
        return version
    elif tipo_version == Versiones.SNAPSHOT:
        import datetime as dt

        ahora = dt.datetime.now()
        dia = ahora.strftime("%w")
        mes = ahora.strftime("%m")
        hora = ahora.strftime("%H")
        return f"{version}-{tipo_version.value}_{hora}{dia}{mes}"
    else:
        return f"{version}-{tipo_version.value}"


VERSION_ACTUAL = obtener_version(NUMERO_VERSION, TIPO_VERSION)
VERSION_EMPAQUETADO = obtener_version_empaquetado(NUMERO_VERSION, TIPO_VERSION)
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

shutil.copy(
    RUTA_ARTEFACTO, Path(RUTA_PAQ_WIN, f"creadorManual-{VERSION_EMPAQUETADO}.jar")
)
shutil.copy(
    RUTA_ARTEFACTO, Path(RUTA_PAQ_LIN, f"creadorManual-{VERSION_EMPAQUETADO}.jar")
)

launcher_windows = open(str(Path(RUTA_PAQ_WIN, "creadorManual.bat")), "x")
launcher_windows.write(f"java -jar creadorManual-{VERSION_EMPAQUETADO}.jar")
launcher_windows.close()

shutil.make_archive(
    str(Path(RUTA_PAQUETES, f"windows-creadorManual-{VERSION_EMPAQUETADO}")),
    "zip",
    RUTA_PAQ_WIN,
)
shutil.make_archive(
    str(Path(RUTA_PAQUETES, f"linux-creadorManual-{VERSION_EMPAQUETADO}")),
    "gztar",
    RUTA_PAQ_WIN,
)
