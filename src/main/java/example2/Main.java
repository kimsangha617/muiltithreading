package example2;

public class Main {

    public static void main(String[] args) {
        Thread thread = new Thread();

        thread.start();
    }

    //Runnable 의 또 다른 객체를 만드는 대신
    // 스레드를 확장하는 새 클래스를 만든다

    private static class NewThread extends Thread { // Thread 클래스를 찾아보녀 Runnable 인터페이스 자체가 구현되어있다
        @Override
        public void run() {
            //Code that executes on the new thread

            System.out.println("Hello from " + Thread.currentThread().getName());
        }
    }
}
