package tests;

import Data.dataDriven;

import java.io.IOException;
import java.util.ArrayList;

public class dataDrivenTest {

    public static void main (String[] args) throws IOException {
        dataDriven d = new dataDriven();
        ArrayList data = d.getData("AddProfile");

        System.out.println(data.get(0));
        System.out.println(data.get(1));
        System.out.println(data.get(2));
        System.out.println(data.get(3));
    }
}
