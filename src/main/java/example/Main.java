package example;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                //Code that will run in a new Thread

                System.out.println("We are now in thread " + Thread.currentThread().getName());
                System.out.println("Current thread priority is " + Thread.currentThread().getPriority());

                throw new RuntimeException(("Intentional Exception"));
            }
        });

        thread.setName("Misbehaving Thread");

        System.out.println("We are in thread : " + Thread.currentThread().getName() + " before starting a new thread");

        // 처음부터 전체 스레드에 해당되는 예외 핸들러를 지정할 수 있다
        // 스레드 내에서 발생한 예외가 어디서도 캐치되지 않으면 핸들러가 호출된다.
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A critical error happened in thread " + t.getName() + " the error is " + e.getMessage());
            }
        });
        thread.start(); // JVM이 새 스레드를 생성해 운영 체제에게 전달한다
        System.out.println("We are in thread : " + Thread.currentThread().getName() + " after starting a new thread");
        Thread.sleep(10000);
    }
}
