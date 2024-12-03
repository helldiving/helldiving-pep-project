package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }


    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getAMessageGivenItsIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromAnIdHandler);

        return app;
    }

   // mapper only for methods that have a REQUEST BODY
   // don't need throws for methods that don't use mapper

    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registration(account);
        if (addedAccount == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }

    
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedAccount = accountService.login(account);
        if (loggedAccount == null) {
            ctx.status(401);
        } else {
            ctx.json(mapper.writeValueAsString(loggedAccount));
        }
    }


    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if (newMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(newMessage));
        }
    }


    private void getAllMessagesHandler(Context ctx) {
        ctx.json(messageService.getAllMessages());
    }


    private void getAMessageGivenItsIdHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessagesById(message_id);
        if (message == null) {
            ctx.result("");
        } else {
        ctx.json(message);
        }
    }


    private void deleteMessageHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleteMessage = messageService.deleteMessage(message_id);
        if (deleteMessage == null) {
            ctx.result("");
        } else {
        ctx.json(deleteMessage);
        }
        
    }


    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatingMessage = messageService.updateMessage(message_id, message.getMessage_text());
        if (updatingMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(updatingMessage));
        }
    }

    private void getAllMessagesFromAnIdHandler(Context ctx) {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getUserMessages(account_id));

    }
}


    // Reqs
    // I should be able to create a new Account on the endpoint POST localhost:8080/register
    // I should be able to verify my login on the endpoint POST localhost:8080/login
    // I should be able to submit a new post on the endpoint POST localhost:8080/messages
    // I should be able to submit a GET request on the endpoint GET localhost:8080/messages
    // I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{message_id}
    // I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}
    // I should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}
    // I should be able to submit a GET request on the endpoint GET localhost:8080/accounts/{account_id}/messages

