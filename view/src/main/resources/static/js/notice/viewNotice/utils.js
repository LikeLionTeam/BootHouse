export function htmlToMarkdown(htmlContent) {
    const rules = [
        { regex: /<img[^>]*src="([^"]*)"[^>]*>/gi, replacement: '![]($1)' },
        { regex: /<h1>(.*?)<\/h1>/gi, replacement: '# $1\n' },
        { regex: /<h2>(.*?)<\/h2>/gi, replacement: '## $1\n' },
        { regex: /<h3>(.*?)<\/h3>/gi, replacement: '### $1\n' },
        { regex: /<strong>(.*?)<\/strong>/gi, replacement: '**$1**' },
        { regex: /<em>(.*?)<\/em>/gi, replacement: '*$1*' },
        { regex: /<ul>/gi, replacement: '' },
        { regex: /<\/ul>/gi, replacement: '' },
        { regex: /<ol>/gi, replacement: '' },
        { regex: /<\/ol>/gi, replacement: '' },
        { regex: /<li>(.*?)<\/li>/gi, replacement: '- $1\n' },
        { regex: /<p>([^<]+)<\/p>/gi, replacement: '$1\n\n' },
        { regex: /<br\s*\/?>/gi, replacement: '\n' },
        { regex: /<hr\s*\/?>/gi, replacement: '---\n' }
    ];

    rules.forEach(rule => {
        htmlContent = htmlContent.replace(rule.regex, rule.replacement);
    });

    return htmlContent;
}

export function insertAtCursor(myField, myValue) {
    if (document.selection) {
        myField.focus();
        const sel = document.selection.createRange();
        sel.text = myValue;
    } else if (myField.selectionStart || myField.selectionStart == '0') {
        const startPos = myField.selectionStart;
        const endPos = myField.selectionEnd;
        myField.value = myField.value.substring(0, startPos) + myValue + myField.value.substring(endPos, myField.value.length);
        myField.selectionStart = startPos + myValue.length;
        myField.selectionEnd = startPos + myValue.length;
    } else {
        myField.value += myValue;
    }
}