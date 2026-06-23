package gg.base.xml;

import java.util.Vector;

import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.Element;

//import static gg.xml.U_XML_ATTRIBUTES.*;

public interface U_XML_COMMENTS {

	static Vector<Comment> detachComments(Element e) { // detach
		Vector<Comment> V = new Vector<Comment>();
		for (Comment x : getComments(e)) {
			x.detach();
			V.add(x);
		}
		return V;
	}

	static void detachAllComments(Element e) { // detach
		detachComments(e);
		for (Element e0 : e.getChildren()) detachAllComments(e0);
	}

	static Vector<Comment> getComments(Element e) { // do not detach
		Vector<Comment> C = new Vector<Comment>();
		for (Content x : new Vector<Content>(e.getContent()))
			if (x instanceof Comment) {
				x.detach();
				C.add((Comment) x);
			}
		return C;
	}

}
