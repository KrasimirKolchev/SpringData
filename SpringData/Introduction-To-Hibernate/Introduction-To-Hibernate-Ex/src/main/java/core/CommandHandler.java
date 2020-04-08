package core;

import java.io.IOException;

public class CommandHandler {
    private AppService appService;

    public CommandHandler() {
        this.appService = new AppService();
    }

    public void handle(String command) throws IOException {

        System.out.println(appService.execute(command));
    }
}
