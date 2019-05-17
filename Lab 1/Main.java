import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Map;
import java.util.Properties;


public class Main {
    public static void main(String args[]) {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        //Reading Text in text Varialble
        String text = "This is demo text and You are Awesome !"; // Add your text here!

        //Creating Annotations for given text
        Annotation document = new Annotation(text);

        //Run all annotators
        pipeline.annotate(document);

        // All Sentences in Doc
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                System.out.println("\n" + token);

                // Tokens
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                System.out.println("Text Annotations:");
                System.out.println(token + ":" + word);
                //POS of Tokens

                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                System.out.println("Lemma Annotations:");
                System.out.println(token + ":" + lemma);
                // Lemmas of tokens


                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                System.out.println("POS: ");
                System.out.println(token + ":" + pos);

                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                System.out.println("NER: ");
                System.out.println(token + ":" + ne);

                System.out.println("\n\n");
            }

            //Parse Tree for Current sentence
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            System.out.println(tree);

            //Depth graph for sentence
            Map<Integer, CorefChain> graph =
                    document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
            System.out.println(graph.values().toString());
        }

    }
}