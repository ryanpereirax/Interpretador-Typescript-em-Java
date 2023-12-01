import java.util.ArrayList;
import java.util.List;

public class AcumuladorMensagem implements EmissorMensagem {

    private List<String> messages = new ArrayList<>();

    @Override
    public void print(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
