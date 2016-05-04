package LanguageProcessing;

import java.util.*;

/**
 * Created by qadirhaqq on 4/24/16.
 */
public class SignToEnglish {



    Map<String, PartsOfSpeech> words = new HashMap<String, PartsOfSpeech>();
    ArrayList<PartsOfSpeech> list = new ArrayList<>();
    Stack<Parts> sentenceStack = new Stack();
    Parts parts;

    private class PartsOfSpeech{
        public ArrayList<PartsOfSpeech> parse(Stack<Parts> speechStack, ArrayList<PartsOfSpeech> list){
            while(speechStack.size() > 0){
                switch (speechStack.pop()){
                    case Sentence:
                        list.add(new Sentence());
                        break;
                    case Time:
                        list.add(new Time());
                        break;
                    case Adjective:
                        list.add(new Adjective());
                        break;
                    case Noun:
                        list.add(new Noun());
                        break;
                    case Subject:
                        list.add(new Subject());
                        break;
                    case Verb:
                        list.add(new Verb());
                        break;
                    case Object:
                        list.add(new Object());
                        break;
                    case Word:
                        list.add(new Word());
                        break;
                    case Question:
                        list.add(new Question());
                        break;
                    default:
                        list.add(new Epsilon());
                }
            }
            return list;
        }
    }

    private enum Parts {
        Sentence, Time, Adjective, Noun, Subject, Object, Verb, Word, Epsilon, Question
    }


    private class Sentence extends PartsOfSpeech{
        public Sentence(){

        }
    }
    private class Time extends PartsOfSpeech{

    }
    private class Adjective extends PartsOfSpeech{

    }
    private class Noun extends PartsOfSpeech{
        String noun = "Noun";
        public String toString(){
            return noun;
        }

    }
    private class Subject extends PartsOfSpeech{

    }
    private class Object extends PartsOfSpeech{

    }
    private class Verb extends PartsOfSpeech{
        String verb = "Verb";
        public String toString(){
            return verb;
        }

    }
    private class Word extends PartsOfSpeech{

    }
    private class Epsilon extends PartsOfSpeech{
        public static final String epsilon = "";
        public static final String test = "EPSILON";

        public Epsilon(){

        }

        public String toString(){
            return epsilon;
        }

    }
    private class Question extends PartsOfSpeech{
        String question = "Question";
        public String toString(){
            return question;
        }
    }

    public String convertToEnglish(String sign){
        words.put("school", new Noun());
        words.put("me", new Noun());
        words.put("go", new Verb());
        words.put("why", new Question());
        words.put("kid", new Noun());
        words.put("in", new Verb());
        words.put("detention", new Noun());
        words.put("I", new Noun());
        String[] sentence =  sign.split(" ");
        for(int i = sentence.length-1; i >= 0; --i){
            if(words.get(sentence[i].toLowerCase()) instanceof Noun)
                    sentenceStack.add(parts.Noun);
            else if(words.get(sentence[i].toLowerCase()) instanceof Verb)
                sentenceStack.add(parts.Verb);
            else if(words.get(sentence[i].toLowerCase()) instanceof Question)
                sentenceStack.add(parts.Question);
            else
                sentenceStack.add(parts.Epsilon);
        }
        PartsOfSpeech thing = new PartsOfSpeech();
        String toReturn = "";
        for(PartsOfSpeech item : thing.parse(sentenceStack, list)){
            toReturn += item.toString() + " ";
        }
        return toReturn;
    }

    public static void main(String[] args) {
        String advanced = "School me go why kid in detention";
        SignToEnglish converter = new SignToEnglish();

        System.out.println(converter.convertToEnglish(advanced));
    }
}
