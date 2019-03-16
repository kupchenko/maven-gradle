package me.kupchenko.maven.multimodule;

public class Application {
    private SomeController controller = new SomeControllerImpl();
    private SomeService service = new SomeServiceImpl();

    public static void main(String[] args) throws InterruptedException {
        Application application = new Application();
        System.out.println("GO GO GO");

        Object something = application.controller.getSomething();
        System.out.println(something);

        application.service.doSomething();
        Thread.sleep(10_000);
    }
}
