package src;
public class philosopherDados {
    private int thinkingCount;
    private int eatingCount;
    public String Name;
    public String Status;

    public philosopherDados(){
        thinkingCount = 0;
        eatingCount = 0;
    }
    public philosopherDados(String name, String status){
        thinkingCount = 0;
        eatingCount = 0;
        this.Name = name;
        this.Status = status;
    }

    public void incrementThinking( int id) {
        thinkingCount++;
        System.out.println("Philosopher " + id + "Number of times thinking: " + thinkingCount);
    }

    public void incrementEating(int id) {
        eatingCount++;
        System.out.println("Philosopher " + id +"Number of times eating: " + eatingCount);
    }

    public void ChangeState(String state) {
        this.Status = state;
        System.out.println("O filosofo foi " + this.Status);
    }
}

