/*

package:gg.vivado.boards.board

<component>
  <simplex>
    <display_name presence="IMPLIED" value="CDATA" />
    <major_group presence="IMPLIED" value="CDATA" />
    <name presence="REQUIRED" value="NMTOKEN" />
    <part_name presence="IMPLIED" value="CDATA" />
    <pin_map_file presence="IMPLIED" value="NMTOKEN" />
    <spec_url presence="IMPLIED" value="CDATA" />
    <sub_type presence="IMPLIED" value="NMTOKEN" />
    <type presence="REQUIRED" ENUMERATION="chip:connector:fpga" />
    <vendor presence="IMPLIED" value="CDATA" />
    <connected_to presence="IMPLIED" value="CDATA" />
  </simplex>
  <complex>
    <component_modes array="component_modes" />
    <description array="description" />
    <drivers array="drivers" />
    <images array="images" />
    <interfaces array="interfaces" />
    <parameters array="parameters" />
    <pins array="pins" />
    <positions array="positions" />
    <additional_supported_parts array="additional_supported_parts" />
    <compatible_connectors array="compatible_connectors" />
  </complex>
</component>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Component = extends Schema
    String:display_name
    String:major_group
    String:name
    String:part_name
    String:pin_map_file
    String:spec_url
    String:sub_type
    String:type
    String:vendor
    String:connected_to
    ArrayList<ComponentModes>:component_modes = new ArrayList<>()
    ArrayList<Description>:description = new ArrayList<>()
    ArrayList<Drivers>:drivers = new ArrayList<>()
    ArrayList<Images>:images = new ArrayList<>()
    ArrayList<Interfaces>:interfaces = new ArrayList<>()
    ArrayList<Parameters>:parameters = new ArrayList<>()
    ArrayList<Pins>:pins = new ArrayList<>()
    ArrayList<Positions>:positions = new ArrayList<>()
    ArrayList<AdditionalSupportedParts>:additional_supported_parts = new ArrayList<>()
    ArrayList<CompatibleConnectors>:compatible_connectors = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Component extends Schema {

public String display_name = null;
public String major_group = null;
public String name = null;
public String part_name = null;
public String pin_map_file = null;
public String spec_url = null;
public String sub_type = null;
public String type = null;
public String vendor = null;
public String connected_to = null;
public ArrayList<ComponentModes> component_modes = new ArrayList<>();
public ArrayList<Description> description = new ArrayList<>();
public ArrayList<Drivers> drivers = new ArrayList<>();
public ArrayList<Images> images = new ArrayList<>();
public ArrayList<Interfaces> interfaces = new ArrayList<>();
public ArrayList<Parameters> parameters = new ArrayList<>();
public ArrayList<Pins> pins = new ArrayList<>();
public ArrayList<Positions> positions = new ArrayList<>();
public ArrayList<AdditionalSupportedParts> additional_supported_parts = new ArrayList<>();
public ArrayList<CompatibleConnectors> compatible_connectors = new ArrayList<>();

}

