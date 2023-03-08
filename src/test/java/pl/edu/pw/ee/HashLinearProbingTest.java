package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import pl.edu.pw.ee.services.HashTable;

public class HashLinearProbingTest { 

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenInitialSizeIsLowerThanOne() {
        // given
        int initialSize = 0;

        // when
        HashTable<Double> unusedHash = new HashLinearProbing<>(initialSize);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenInputElemIsNull() {
        // given
        HashTable<Double> hash = new HashLinearProbing<>();
        Double elem = null;

        // when
        hash.put(elem);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenInputElemIsDelString() {
        // given
        HashTable<String> hash = new HashLinearProbing<>();
        String elem = "ówér人繞босаḍḍ";

        // when
        hash.put("ówér人繞босаḍḍ");

        // then
        assert false;
    }

    @Test
    public void shouldCorrectlyAddNewElemsWhenNotExistInHashTable() {
        // given
        HashTable<String> emptyHash = new HashLinearProbing<>();
        String newEleme = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(emptyHash);
        emptyHash.put(newEleme);
        int nOfElemsAfterPut = getNumOfElems(emptyHash);

        // then
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);
    }

    @Test
    public void shouldCorrectlyAddGetAndRemoveNewElemsWithSameHashIndexToHashTable() {
        // given
        HashTable<Integer> hash = new HashLinearProbing<>(10);
        Integer num1 = 1;
        Integer num2 = 11;
        Integer num3 = 101;
        Integer num4 = 10001;

        // when
        int nOfElemsBeforePut = getNumOfElems(hash);
        hash.put(num1);
        hash.put(num2);
        hash.put(num3);
        hash.put(num4);
        int nOfElemsAfterPut = getNumOfElems(hash);

        // then
        assertEquals(true, 1 == num1.hashCode() % 10);
        assertEquals(true, 1 == num2.hashCode() % 10);
        assertEquals(true, 1 == num3.hashCode() % 10);
        assertEquals(true, 1 == num4.hashCode() % 10);
        assertEquals(num1, hash.get(num1));
        assertEquals(num2, hash.get(num2));
        assertEquals(num3, hash.get(num3));
        assertEquals(num4, hash.get(num4));
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(4, nOfElemsAfterPut);
        hash.delete(num1);
        hash.delete(num2);
        hash.delete(num3);
        hash.delete(num4);
        assertEquals(null, hash.get(num1));
        assertEquals(null, hash.get(num2));
        assertEquals(null, hash.get(num3));
        assertEquals(null, hash.get(num4));
        assertEquals(0, getNumOfElems(hash));
    }

    @Test
    public void shouldCorrectlyAddAndGetSameElementOfNewElems() {
        // given
        HashTable<String> hash = new HashLinearProbing<>();
        int size = 1000000;
        String newEleme = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(hash);
        for (int i = 0; i < size; i++) {
            hash.put(newEleme);
        }
        int nOfElemsAfterPut = getNumOfElems(hash);

        // then
        for (int i = 0; i < size; i++) {
            assertEquals(newEleme, hash.get(newEleme));
        }
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(1, nOfElemsAfterPut);

    }

    @Test
    public void shouldCorrectlyAddAndGetBigAmountOfNewElems() {
        // given
        HashTable<Double> hash = new HashLinearProbing<>();
        int size = 1000000;
        Double randomNums[] = GenerateNumbers(size);

        // when
        int nOfElemsBeforePut = getNumOfElems(hash);
        for (Double num : randomNums) {
            hash.put(num);
        }
        int nOfElemsAfterPut = getNumOfElems(hash);

        // then
        for (Double num : randomNums) {
            assertEquals(num, hash.get(num));
        }
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(size, nOfElemsAfterPut);

    }

    @Test
    public void shouldCorrectlyAddNewElemWhitchHashCodeIsEqualToHashSize() {
        // given
        HashTable<String> hash = new HashLinearProbing<>(30);
        String newEleme = "nothing special";

        // when
        int nOfElemsBeforePut = getNumOfElems(hash);
        for (int i = 0; i < 30; i++) {
            hash.put(newEleme + (char) i);
        }
        int nOfElemsAfterPut = getNumOfElems(hash);

        // then
        for (int i = 0; i < 30; i++) {
            assertEquals(newEleme + (char) i, hash.get(newEleme + (char) i));
        }
        assertEquals(0, nOfElemsBeforePut);
        assertEquals(30, nOfElemsAfterPut);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenOtputElemIsNull() {
        // given
        HashTable<Double> hash = new HashLinearProbing<>();
        Double elem = null;

        // when
        hash.get(elem);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenDeleteElemIsNull() {
        // given
        HashTable<Double> hash = new HashLinearProbing<>();
        Double elem = null;

        // when
        hash.delete(elem);

        // then
        assert false;
    }

    @Test
    public void shouldCorrectlyGetElemNotContainedInHash() {
        // given
        HashTable<String> emptyHash = new HashLinearProbing<>();
        String elem = "nothing special";
        String differentElem = "ferdydurke";

        // when
        emptyHash.put(elem);
        String output = emptyHash.get(differentElem);

        // then
        assertEquals(null, output);
    }

    @Test
    public void shouldCorrectlyAddAndDeleteBigAmountOfNewElems() {
        // given
        HashTable<Double> hash = new HashLinearProbing<>();
        int size = 1000000;
        Double randomNums[] = GenerateNumbers(size);

        // when
        int nOfElemsBeforeDelete = getNumOfElems(hash);
        for (Double num : randomNums) {
            hash.put(num);
        }
        for (Double num : randomNums) {
            hash.delete(num);
        }
        int nOfElemsAfterDelete = getNumOfElems(hash);

        // then
        assertEquals(0, nOfElemsBeforeDelete);
        assertEquals(0, nOfElemsAfterDelete);

    }

    @Test
    public void shouldCorrectlyRemoveSameElementTwoTimesThenAddItToHashList() {
        //given
        HashTable<Double> hash = new HashLinearProbing<>(1);
        hash.put(103.5);

        //when 
        hash.delete(103.5);
        hash.delete(103.5);
        assertEquals(null, hash.get(103.5));
        hash.put(123123123123123123123.5);

        //then
        assertEquals(123123123123123123123.5, hash.get(123123123123123123123.5));
    }

    @Test
    public void shouldCorrectlyAddGetAndRemoveSeveralStringElems() throws IOException {
        //given
        List<String> stringList = new ArrayList<>();
        HashTable<String> hash = new HashLinearProbing<>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader("wordlist.100000.txt"));

        while ((line = reader.readLine()) != null) {
            stringList.add(line);
            hash.put(line);
        }

        //when
        for (int i = 0; i < 50000; i++) {
            hash.delete(stringList.get(i));
        }

        //then
        for (int i = 0; i < 50000; i++) {
            assertEquals(null, hash.get(stringList.get(i)));
        }
        for (int i = 50000; i < 100000; i++) {
            assertEquals(stringList.get(i), hash.get(stringList.get(i)));
        }
    }

    private int getNumOfElems(HashTable<?> hash) {
        String fieldNumOfElems = "nElems";
        try {
            System.out.println(hash.getClass().getSuperclass().getName());
            Field field = hash.getClass().getSuperclass().getDeclaredField(fieldNumOfElems);
            field.setAccessible(true);

            int numOfElems = field.getInt(hash);

            return numOfElems;

        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void performanceTestNonPrimeNumber() throws FileNotFoundException, IOException {

        String line;
        Double[] timeArr = new Double[10];
        int[] hashSizesArr = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144};
        List<String> stringList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("wordlist.100000.txt"));
        while ((line = reader.readLine()) != null) {
            stringList.add(line);
        }

        for (int i = 0; i < 10; i++) {
            double[] tmpArr = new double[30];

            for (int j = 0; j < 30; j++) {
                double time = 0;
                HashTable<String> hash = new HashLinearProbing<>(hashSizesArr[i]);
                for (int z = 0; z < stringList.size(); z++) {
                    String listOtput = stringList.get(z);
                    double timeBeforeInsertion = System.nanoTime() * Math.pow(10, -9);
                    hash.put(listOtput);
                    double timeAfterInsertion = System.nanoTime() * Math.pow(10, -9);
                    time += timeAfterInsertion - timeBeforeInsertion;
                }
                tmpArr[j] = time;
            }
            Arrays.sort(tmpArr);
            tmpArr = Arrays.copyOfRange(tmpArr, 9, 19);
            timeArr[i] = avg(tmpArr);
        }
        System.out.println(Arrays.toString(timeArr));
    }

    private double avg(double[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!!!");
        }
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        return sum / data.length;
    }

    public Double[] GenerateNumbers(int quantity) {
        Double[] randomNums = new Double[quantity];
        Random generator = new Random();
        for (int i = 0; i < quantity; i++) {
            randomNums[i] = generator.nextDouble() * Double.MAX_VALUE;
        }
        return randomNums;
    }
}
