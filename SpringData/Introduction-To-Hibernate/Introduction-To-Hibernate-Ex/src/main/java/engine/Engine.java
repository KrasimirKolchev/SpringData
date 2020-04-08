package engine;

import core.CommandHandler;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Engine implements Runnable {
    private BufferedReader reader;
    private CommandHandler handler;

    public Engine() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.handler = new CommandHandler();
    }


    @SneakyThrows
    @Override
    public void run() {

        try {
            while (true) {
                System.out.println("---> Enter exercise to test: <---\n---> write 'End' to end the program");
                String input = reader.readLine();

                if ("End".equals(input)) {
                    break;
                }

                handler.handle(input);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
