#include <iostream>
#include <string>

int main() {
  std::string nombre;
  std::cout << "Ingresa tu nombre"
            << "\n> ";
  std::cin >> nombre;
  std::cout << "Hola " << nombre << std::endl;

  return 0;
}
