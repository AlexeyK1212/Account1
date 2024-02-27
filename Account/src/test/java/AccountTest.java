import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void undo() throws undoException {

        Account ac = new Account("Начальное имя");

        ac.updateCurrencyAccounts(810,100);
        ac.updateName("Василий Иванов");
        ac.updateCurrencyAccounts(810,300);
        ac.undo();


        assertEquals(ac.getValueByCurrencyCode(840),0);
        assertEquals(ac.getValueByCurrencyCode(978),0);
        assertEquals(ac.getValueByCurrencyCode(810),100);



    }

    @Test
    void save() throws undoException {
        Account ac = new Account("Начальное имя");

        ac.updateName("Василий Иванов");
        ac.updateCurrencyAccounts(810,300);

        // сохраняем
        Account.AccountMemento memento = ac.save();

        // исзменяем
        ac.updateName("Василий Петров");
        ac.updateCurrencyAccounts(810,400);

        //делаем restore
        ac.restore(memento);

        assertEquals(ac.getName(),"Василий Иванов");

        assertEquals(ac.getValueByCurrencyCode(840),0);
        assertEquals(ac.getValueByCurrencyCode(978),0);
        assertEquals(ac.getValueByCurrencyCode(810),300);



    }
}