import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class Operacion{
   public int num;
   public String tipo,arg,value,result;

 public Operacion(int num, String tipo, String arg, String value, String result){
     this.num = num;
     this.tipo = tipo;
     this.arg = arg;
     this.value = value;
     this.result = result;

 }

}

public class Ejemplo{


  public static void main(String[] args){
    Ejemplo instance1 = new Ejemplo();

    Operacion Op1 = new Operacion(1,"Leer","","","");
    Operacion Op2 = new Operacion(1,"Escribir","","","");
    Operacion Op3 = new Operacion(1,"Commit","","","");
    Operacion Op4 = new Operacion(1,"Abort","","","");



    Thread t1 = new Thread(new Transaccion(Op1), "Operacion-1");
    Thread t2 = new Thread(new Transaccion(Op2), "Operacion-2");
    Thread t3 = new Thread(new Transaccion(Op3), "Operacion-3");
    //Thread t3 = new Thread(new Worker(new StaticMethodExample()), "Operacion-3");

    long  startTime = System.currentTimeMillis();
    t1.start();
    t2.start();
  //  t3.start();
  }
}

class Transaccion implements Runnable{
  private  Operacion Op;

  private final Lock lock = new ReentrantLock();

  public Transaccion(Operacion P){
      this.Op = P;
  }

  public void run() {
    Random random = new Random();
    // Loop 10
    for (int counter = 0; counter <= 10; counter++) {
      try {
        Thread.sleep(random.nextInt(5));
      } catch (InterruptedException e) {
        System.out.println(Thread.currentThread().getName());
    }
      this.aplicar();
      }
    }

  public boolean isBloqueado(){

    Boolean mainLock = false;
    Boolean OtroLock = false;

    if(this.Op.tipo == "Leer" || this.Op.tipo =="Escribir"){
        try{
              mainLock = lock.tryLock();
            //  OtroLock = P.lock.tryLock();

        }
        finally{
          if(!mainLock){
          lock.unlock();
          }
        //  OtroLock.lock.unlock();
        }

    }
    else if(this.Op.tipo == "Commit" || this.Op.tipo =="Abort    "){
      System.out.print("En espera de liberacion del seguro   ");
      lock.unlock();
      return false;
    }
    return mainLock;


  }

  public void aplicar() {
    // Check if Lock is already exist?
    if (isBloqueado()) {
      try {
        System.out.println(Thread.currentThread().getName()+ " Comienza la operacion de ---->:" + this.Op.tipo+"   ");
        System.out.println(Thread.currentThread().getName() + " Bloqueo Aplicado    ");
      } finally {
        lock.unlock();
        System.out.println(Thread.currentThread().getName()+"  Bloqueo Liberado    ");
      }
    } else {
      System.out.println("Bloqueo Activado en espera de Liberacion \n    ");
    }
  }


}
