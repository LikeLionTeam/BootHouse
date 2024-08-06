package likelion.eight.domain.notice.converter;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.stereotype.Component;

@Component
public class MarkdownConverter {
    public String convertMarkdownToHtml(String markdown) {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer htmlRenderer = HtmlRenderer.builder(options).build();

        return htmlRenderer.render(parser.parse(markdown));
    }

    public String convertHtmlToMarkdown(String html) {
        MutableDataSet options = new MutableDataSet();
        return FlexmarkHtmlConverter.builder(options).build().convert(html);
    }
}
