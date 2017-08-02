package com.worksap.nlp.sudachi;

import java.io.IOException;
import java.util.List;
import com.worksap.nlp.sudachi.dictionary.Grammar;

public abstract class OovProviderPlugin extends Plugin {

    public abstract void setUp(Grammar grammar) throws IOException;

    public abstract List<LatticeNode> provideOOV(InputText<?> inputText,
                                                 int offset,
                                                 boolean hasOtherWords);

    List<LatticeNode> getOOV(UTF8InputText inputText, int offset,
                             boolean hasOtherWords) {
        int charOffset = inputText.getText().length() - inputText.getByteOffsetText(offset).length();
        List<LatticeNode> nodes = provideOOV(inputText, charOffset, hasOtherWords);
        for (LatticeNode node : nodes) {
            LatticeNodeImpl n = (LatticeNodeImpl)node;
            n.begin = offset;
            n.end = offset + node.getWordInfo().getLength();
        }
        return nodes;
    }

    LatticeNode createNode() {
        LatticeNode node = new LatticeNodeImpl();
        node.setOOV();
        return node;
    }
}