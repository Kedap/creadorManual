import java.util.Scanner;

public class Alumno {
    String nombre;
    Integer edad;
    Integer grupo;

    public static void main(String[] args) {
        Alumno daniel = new Alumno();

        System.out.println("Ingresa el nombre para tu alumno");
        Scanner sc = new Scanner(System.in);
        daniel.nombre = sc.nextLine();

        System.out.println("Ingresa el edad para tu alumno");
        daniel.edad = Integer.valueOf(sc.nextLine());

        System.out.println("Ingresa el grupo para tu alumno");
        daniel.grupo = Integer.valueOf(sc.nextLine());

        daniel.imprimir();

        sc.close();
    }

    private void imprimir() {
        System.out.println(this.nombre);
        System.out.println(edad);
        System.out.println(grupo);
    }
}
