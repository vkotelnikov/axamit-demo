package kotelnikov.axamitdemo.tasks;

import org.springframework.stereotype.Service;

@Service
public class NumSystemsConverter {

    public static void main(String[] args) {
        System.out.println(convertToNewBase(23, 1));
    }

    //в задаче не уточнялось, что возвращать, когда выбрана система
    //счисления меньше 2 или больше 36. Возвращаем текст
    public static String convertToNewBase( Integer number, int newBase ){
        if( newBase < 2 || newBase > 36 ){
            return "New base is out of range";
        }
        return Integer.toString(number, newBase);
    }
}
