package Models;

import java.util.List;

public class TextDocumentModels {
    private String fileName;
    private List<String> content;

    public TextDocumentModels(String fileName, List<String> content) {
        this.fileName = fileName;
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}

