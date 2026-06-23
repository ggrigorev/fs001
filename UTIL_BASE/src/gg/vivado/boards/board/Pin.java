/*

package:gg.vivado.boards.board

<pin>
  <simplex>
    <connector_index presence="IMPLIED" value="NMTOKEN" />
    <name presence="REQUIRED" value="NMTOKEN" />
    <index presence="REQUIRED" value="CDATA" />
    <iostandard presence="IMPLIED" value="CDATA" />
    <loc presence="IMPLIED" value="CDATA" />
    <drive presence="IMPLIED" value="CDATA" />
    <slew presence="IMPLIED" value="CDATA" />
    <diff_term presence="IMPLIED" value="CDATA" />
    <output_impedance presence="IMPLIED" value="CDATA" />
    <ibuf_low_pwr presence="IMPLIED" value="CDATA" />
    <odt presence="IMPLIED" value="CDATA" />
    <equalization presence="IMPLIED" value="CDATA" />
    <pre_emphasis presence="IMPLIED" value="CDATA" />
    <dqs_bias presence="IMPLIED" value="CDATA" />
    <delay_value presence="IMPLIED" value="CDATA" />
    <iobdelay presence="IMPLIED" value="CDATA" />
    <lvds_pre_emphasis presence="IMPLIED" value="CDATA" />
    <pull_type presence="IMPLIED" value="CDATA" />
    <voh presence="IMPLIED" value="CDATA" />
  </simplex>
</pin>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Pin = extends Schema
    String:connector_index
    String:name
    String:index
    String:iostandard
    String:loc
    String:drive
    String:slew
    String:diff_term
    String:output_impedance
    String:ibuf_low_pwr
    String:odt
    String:equalization
    String:pre_emphasis
    String:dqs_bias
    String:delay_value
    String:iobdelay
    String:lvds_pre_emphasis
    String:pull_type
    String:voh

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Pin extends Schema {

public String connector_index = null;
public String name = null;
public String index = null;
public String iostandard = null;
public String loc = null;
public String drive = null;
public String slew = null;
public String diff_term = null;
public String output_impedance = null;
public String ibuf_low_pwr = null;
public String odt = null;
public String equalization = null;
public String pre_emphasis = null;
public String dqs_bias = null;
public String delay_value = null;
public String iobdelay = null;
public String lvds_pre_emphasis = null;
public String pull_type = null;
public String voh = null;

}

