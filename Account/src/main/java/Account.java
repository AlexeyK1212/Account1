

import java.util.HashMap;
import java.util.Stack;

//для выполнения команд по отмене  действий над аккаунтом
interface Command {
    public abstract void undo();
}


enum Currencys {
    RUR(810),
    USD(840),
    EUR(978);
    public final int i;
    Currencys(int i) {
        this.i=i;
    }
}





// Класс аккаунта
public class Account {
    private String name;
    private HashMap<Integer, Integer> currencyAccounts;
    private Stack<Command> history;






    public Account(String name) throws undoException {
        if(name.isEmpty()) {
            throw new undoException("Название счета не может быть пустым");
        }
        if(name=="") {
            throw new undoException("Название счета не может быть пустым");
        }

        this.name = name;
        this.currencyAccounts = new HashMap<>();

        for (Currencys curr : Currencys.values())
            currencyAccounts.put(curr.i,0);


        this.history = new Stack<>();
    }

    // Метод для отмены последней операции
    public void undo() throws undoException {
        if (!history.isEmpty()) {
            history.pop().undo();
        }
        else throw new undoException("Невозможно откатить");
    }

    // метод изменения строки
    public void updateName(String newValue) throws undoException {

        if(newValue.isEmpty()) {
            throw new undoException("Название счета не может быть пустым");
        }
        if(newValue=="") {
            throw new undoException("Название счета не может быть пустым");
        }

        final String oldValue = name;
        name = newValue;
        history.push(new Command() {
            @Override
            public void undo() {
                name = oldValue;
            }
        });
    }

    // метода изменения HashMap
    public void updateCurrencyAccounts(Integer key, Integer value) throws undoException {

        if(key<=0) {
            throw new undoException("Код валюты должен быть больше нуля");
        }

        final Integer oldValue = currencyAccounts.get(key);
        currencyAccounts.put(key, value);
        history.push(new Command() {
            @Override
            public void undo() {
                if (oldValue == null) {
                    currencyAccounts.remove(key);
                } else {
                    currencyAccounts.put(key, oldValue);
                }
            }
        });
    }

    // Геттеры для полей (для проверки состояния)
    public String getName() {
        return name;
    }

    public HashMap<Integer, Integer> getCurrencyAccounts() {
        return new HashMap<Integer, Integer>(currencyAccounts);
    }

    public int getValueByCurrencyCode(int CurrencyCode) {
        return currencyAccounts.get(CurrencyCode);
    }


    // Метод для сохранения состояния

    public AccountMemento save() {

        return new AccountMemento(name, currencyAccounts);

    }


    // Метод для восстановления состояния

    public void restore(AccountMemento memento) {

        this.name = memento.getStringFieldState();

        this.currencyAccounts = new HashMap<>(memento.getHashMapFieldState());

    }


    public class AccountMemento {

        private final String stringFieldState;

        private final HashMap<Integer, Integer> hashMapFieldState;



        public AccountMemento(String stringField, HashMap<Integer, Integer> hashMapField) {

            this.stringFieldState = stringField;

            this.hashMapFieldState = new HashMap<>(hashMapField);

        }



        // Получаем состояние stringField

        public String getStringFieldState() {

            return stringFieldState;

        }



        // Получаем состояние hashMapField

        public HashMap<Integer, Integer> getHashMapFieldState() {

            return hashMapFieldState;

        }

    }


}

class undoException extends Exception {
    public undoException (String msg)
    {
        super(msg);
    }


}