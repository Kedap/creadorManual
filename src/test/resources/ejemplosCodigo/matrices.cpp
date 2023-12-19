#include <iostream>

int main() {
  int gato[3][3];
  for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
      std::cout << "Ingresa el valor para la fila" << i + 1 << " columna "
                << j + 1 << "\n> ";
      std::cin >> gato[i][j];
    }
  }

  return 0;
}
