package dr.ui.table.cell;

/**
 *
 * @author Graham Smith
 */
public class EditableTableCell<S extends Object, T extends String> extends AbstractEditableTableCell<S, T> {
    public EditableTableCell() {
    }
    @Override
    protected String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
    @Override
    protected void commitHelper( boolean losingFocus ) {
        commitEdit(((T) textField.getText()));
    }
     
}
