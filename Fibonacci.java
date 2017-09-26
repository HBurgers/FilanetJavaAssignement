package com.Heinrich.JavaAssignment;
import java.util.ArrayList;

public class Fibonacci {
    ArrayList list = null;

    public Fibonacci()
    {
        list = new ArrayList();

        popList();
    }

    public void printFabonacci()
    {
        System.out.println(list.toString());
    }

    private void popList()
    {
        int second = 2;
        int third = 3;

        list.add(1);
        list.add(2);

        while(third < 4000000)
        {
            list.add(third);

            second = third;
            third = third + second;
        }
    }

    public ArrayList getList()
    {
        return list;
    }
}
