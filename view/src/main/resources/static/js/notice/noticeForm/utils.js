export function insertAtCursor(field, value) {
    if (document.selection) {
        field.focus();
        const sel = document.selection.createRange();
        sel.text = value;
    } else if (field.selectionStart || field.selectionStart == '0') {
        const startPos = field.selectionStart;
        const endPos = field.selectionEnd;
        field.value = field.value.substring(0, startPos) + value + field.value.substring(endPos, field.value.length);
        field.selectionStart = startPos + value.length;
        field.selectionEnd = startPos + value.length;
    } else {
        field.value += value;
    }
}