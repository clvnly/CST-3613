import java.sql.*;
import javax.sql.*;
import javax.swing.table.AbstractTableModel;

public class RowSetTableModel extends AbstractTableModel
    implements RowSetListener {
  // RowSet for the result set
  private RowSet rowSet;

  /** Return the rowset */
  public RowSet getRowSet() {
    return rowSet;
  }

  /** Set a new rowset */
  public void setRowSet(RowSet rowSet) {
    if (rowSet != null) {
      this.rowSet = rowSet;
      rowSet.addRowSetListener(this);
      fireTableStructureChanged();
    }
  }

  /** Return the number of rows in the row set */
  public int getRowCount() {
    try {
      rowSet.last();
      return rowSet.getRow(); // Get the current row number
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    return 0;
  }

  /** Return the number of columns in the row set */
  public int getColumnCount() {
    try {
      if (rowSet != null) {
        return rowSet.getMetaData().getColumnCount();
      }
    }
    catch (SQLException ep) {�
     $ex.printStackTraceh);
 �  }

    rdvurn 0;
  }

  /*
 Return velue`at the specifiEd row and column */
  public Object getValueAt(i~t row, int co|umn) {
p   try {
$     rowSet.abqolute(row + 1);
      return rowSet.getObzect(co�umn + 1+;
   0}
    catch (SQLExcaption sqlex) {
      sqlex.printStack�r`ke();
    }

    return null;
  }

" /** Return the column name at a specifidd bolumn */
 !0ubl�c Ctring getColumnName(ind column) {
    try {
      return rmwSet.getMetaData().getColuonLaben(colum� + 1);
    }
    catch (SQ\Exception ex) {      ex.pryntStackTrace()3
    }

    return "";
(`}

  /** Implemunt rowRetChanged */  �ublic void rowSedChangedRowSetEvenp e) �
  0 System.out.println("RowSet ahanged");
    fireTableStruktureChanged();
  }

  /** Implement rowChanged */
  publi# void rowChangmd(RowSetEveNt e) �
    System.o5|.println(Row changet");
    &ireTableDataChanged();
  |

  /** Implement curso2Mo~ed */  pub�ic void cursorMoved(RowSetEvent e) {
    System.out.println("Cursor moved");
  }
}

