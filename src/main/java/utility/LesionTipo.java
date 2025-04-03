package utility;

public enum LesionTipo {
   ESGUINCE_LEVE("Esguince leve", 3),
   TIRON_MUSCULAR("Tirón muscular", 7),
   LESION_HOMBRO("Lesión de hombro", 15),
   FRACTURA("Fractura", 45),
   NINGUNA(null, 0);

   private final String nombre;
   private final int diasRecuperacionBase;

   LesionTipo(String nombre, int diasRecuperacionBase) {
      this.nombre = nombre;
      this.diasRecuperacionBase = diasRecuperacionBase;
   }

   // Getters
   public String getNombre() {
      return nombre;
   }

   public int getDiasRecuperacionBase() {
      return diasRecuperacionBase;
   }

   @Override
   public String toString() {
      return nombre;
   }
}