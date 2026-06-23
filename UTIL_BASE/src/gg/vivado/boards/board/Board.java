/*

package:gg.vivado.boards.board

<board>
  <simplex>
    <display_name presence="REQUIRED" value="CDATA" />
    <name presence="REQUIRED" value="NMTOKEN" />
    <preset_file presence="IMPLIED" value="NMTOKEN" />
    <schema_version presence="REQUIRED" value="NMTOKEN" />
    <url presence="IMPLIED" value="CDATA" />
    <vendor presence="REQUIRED" value="NMTOKEN" />
    <supports_ced presence="IMPLIED" value="CDATA" />
  </simplex>
  <complex>
    <images array="images" />
    <compatible_board_revisions array="compatible_board_revisions" />
    <file_version array="file_version" />
    <description array="description" />
    <parameters array="parameters" />
    <power_rails array="power_rails" />
    <data_properties array="data_properties" />
    <jumpers array="jumpers" />
    <components array="components" />
    <jtag_chains array="jtag_chains" />
    <connections array="connections" />
    <ip_associated_rules array="ip_associated_rules" />
  </complex>
</board>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Board = extends Schema
    String:display_name
    String:name
    String:preset_file
    String:schema_version
    String:url
    String:vendor
    String:supports_ced
    ArrayList<Images>:images = new ArrayList<>()
    ArrayList<CompatibleBoardRevisions>:compatible_board_revisions = new ArrayList<>()
    ArrayList<FileVersion>:file_version = new ArrayList<>()
    ArrayList<Description>:description = new ArrayList<>()
    ArrayList<Parameters>:parameters = new ArrayList<>()
    ArrayList<PowerRails>:power_rails = new ArrayList<>()
    ArrayList<DataProperties>:data_properties = new ArrayList<>()
    ArrayList<Jumpers>:jumpers = new ArrayList<>()
    ArrayList<Components>:components = new ArrayList<>()
    ArrayList<JtagChains>:jtag_chains = new ArrayList<>()
    ArrayList<Connections>:connections = new ArrayList<>()
    ArrayList<IpAssociatedRules>:ip_associated_rules = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Board extends Schema {

public String display_name = null;
public String name = null;
public String preset_file = null;
public String schema_version = null;
public String url = null;
public String vendor = null;
public String supports_ced = null;
public ArrayList<Images> images = new ArrayList<>();
public ArrayList<CompatibleBoardRevisions> compatible_board_revisions = new ArrayList<>();
public ArrayList<FileVersion> file_version = new ArrayList<>();
public ArrayList<Description> description = new ArrayList<>();
public ArrayList<Parameters> parameters = new ArrayList<>();
public ArrayList<PowerRails> power_rails = new ArrayList<>();
public ArrayList<DataProperties> data_properties = new ArrayList<>();
public ArrayList<Jumpers> jumpers = new ArrayList<>();
public ArrayList<Components> components = new ArrayList<>();
public ArrayList<JtagChains> jtag_chains = new ArrayList<>();
public ArrayList<Connections> connections = new ArrayList<>();
public ArrayList<IpAssociatedRules> ip_associated_rules = new ArrayList<>();

}

