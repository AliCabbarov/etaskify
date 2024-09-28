package etaskify.util;

import etaskify.model.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    public String confirmEmailSubjectBuilder(String token, String username) {
        return "<p> Hi, " + username + ", <p>" +
                "<p>Thank you for registering with us," +
                "Please, follow the link below to complete your registration.<p>" +
                "<a href=\"" + createConfirmUrl(token) + "\">Verify your email to active your account<a>" +
                "<p> Thank you <br> Users Registration Portal Service";

    }

    private String createConfirmUrl(String token) {
        return "http://35.222.111.242:80/api/v1/users/confirmation?token=" + token;
    }
    public String taskMessageSubjectBuilder(Task task) {
        return  "Hi, your next task" +
                "\ntitle      : " + task.getTitle() +
                "\ndescription:" + task.getDescription() +
                "\ndeadLine   : " + task.getDeadLine();
    }
}
