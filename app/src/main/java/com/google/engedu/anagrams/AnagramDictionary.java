/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    int wordlength=DEFAULT_WORD_LENGTH;
    private ArrayList<String> wordlist;
    private HashSet<String> wordset;
    private HashMap<String ,ArrayList<String>>letterstoWord;
    private HashMap<Integer,ArrayList<String>>sizeTowords;

    public AnagramDictionary(Reader reader) throws IOException {
       wordlist =new ArrayList<>();
       wordset=new HashSet<>();
       letterstoWord =new HashMap<>();
       sizeTowords=new HashMap<>();
       BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordlist.add(word);
            wordset.add(word);
            String sortedStr=sortLetters(word);
            ArrayList<String> value;
            if(letterstoWord.containsKey(sortedStr))
            {
                ArrayList<String> temp=letterstoWord.get(sortedStr);
                if(!temp.contains(word)){
                    temp.add(word);
                }
                letterstoWord.put(sortedStr,temp);
            }
            if(!letterstoWord.containsKey(sortedStr)){
                value=new ArrayList<String>();
                value.add(word);
                letterstoWord.put(sortedStr,value);
            }
            int len=sortedStr.length();
            if(sizeTowords.containsKey(len)){
                sizeTowords.get(len).add(word);
            }
            else
            {
                ArrayList<String> temp1=new ArrayList<>();
                temp1.add(word);
                sizeTowords.put(len,temp1);

            }

        }
    }

    public boolean isGoodWord(String word, String base) {
        return wordset.contains(word)&& !word.contains(base) ;
    }


    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String retnewstring=new String();
        String temp;
        ArrayList temparrlist;
        String geti=new String();
       for (char j='a';j<='z';j++)
       {
           temp=word.concat(""+j);
           retnewstring=sortLetters(temp);



               if(letterstoWord.containsKey(retnewstring)){
                   temparrlist=letterstoWord.get(retnewstring);
                   for(int i=0;i<temparrlist.size();i++){
                       geti=(String)temparrlist.get(i);
                       if(!geti.contains(word)){
                           result.add(geti);
                       }
                   }
               }

       }
        return result;
    }

    public String sortLetters(String targetword) {

         char[] chars= targetword.toCharArray();
        Arrays.sort(chars);

        return new String(chars);
           }



    public String pickGoodStarterWord() {
        ArrayList<String> values=sizeTowords.get(wordlength);
        if(wordlength<MAX_WORD_LENGTH){
            wordlength++;
        }
        while(true){
            String randomWord=values.get(random.nextInt(values.size()));
            if(getAnagramsWithOneMoreLetter(randomWord).size()>=MIN_NUM_ANAGRAMS){
                return randomWord;
            }
        }
    }

    /*public List<String> getAnagrams(String currentWord) {
        ArrayList<String> result=new ArrayList<>();
        String  retword=sortLetters(currentWord);
        for(String temp:wordlist){
            String retwordlist=sortLetters(temp);
            if(retword.length()==temp.length())
            {
                if(retword.equals(retwordlist)){
                    result.add(temp);
                }
            }
        }
   return result;



    }*/

}
