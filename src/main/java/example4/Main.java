package example4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * factorial을 계산하는 예시
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);
        // we want to calculate the !0, !3435, !35435, !2324, !4656, !23, !2435, !5566

        List<FactorialThread> threads = new ArrayList<>();

        //모든 입력 번호에 대해 개별적인 factorial thread 객체 생성, 입력 숫자를 생성자에게 전달
        for(long inputNumber : inputNumbers){
            threads.add(new FactorialThread(inputNumber));

        }

        for(Thread thread : threads){
            thread.setDaemon(true);
            thread.start();
        }

        //factorial thread 가 작업을 마칠 때까지 main thread를 기다리게 할것임
        // 그러려면 thread.join 메서드가 호출된 모든 스레드에 이 명령을 반복해야함
        // 모든 스레드의 thread.join 메서드는 thread가 종료되어야만 반환된다.
        // 그리고 main 스레드가 루프를 완료할 때까지 모든 factorial 스레드는 무조건 작업을 마치게 된다.
        for(Thread thread : threads){
            thread.join(2000); // 각 작업 스레드가 기다릴 시간을 결정
        }

        //factorial thread 에서 결과를 가져온 다음 출력
        for(int i = 0; i < inputNumbers.size(); i++){
            FactorialThread factorialThread = threads.get(i);
            if(factorialThread.isFinished()){
                System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress");
            }
        }
    }

    public static class FactorialThread extends Thread{
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO; // 결과값이 너무 크면 오버플로우가 발생할 수 있어 오래 사용 못함
        private boolean isFinished = false;

        public FactorialThread(long inputNumber){
            this.inputNumber = inputNumber;
        }

        @Override
        public void run(){
            this.result = factorial(inputNumber);
            this.isFinished = true;
        }

        private BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for(long i = n; i > 0; i--){
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public boolean isFinished(){
            return isFinished;
        }

        public BigInteger getResult(){
            return result;
        }
    }
}
