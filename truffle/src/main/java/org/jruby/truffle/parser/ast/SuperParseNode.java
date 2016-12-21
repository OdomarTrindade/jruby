/*
 ***** BEGIN LICENSE BLOCK *****
 * Version: EPL 1.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Eclipse Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/epl-v10.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2001-2002 Jan Arne Petersen <jpetersen@uni-bonn.de>
 * Copyright (C) 2001-2002 Benoit Cerrina <b.cerrina@wanadoo.fr>
 * Copyright (C) 2002-2004 Anders Bengtsson <ndrsbngtssn@yahoo.se>
 * Copyright (C) 2004 Thomas E Enebo <enebo@acm.org>
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the EPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the EPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/
package org.jruby.truffle.parser.ast;

import org.jruby.truffle.parser.ast.visitor.NodeVisitor;
import org.jruby.truffle.parser.lexer.SimpleSourcePosition;

import java.util.List;

/**
 * A call to super(...) with arguments to a method.
 */
public class SuperParseNode extends ParseNode implements BlockAcceptingParseNode {
    private final ParseNode argsNode;
    private ParseNode iterNode;

    public SuperParseNode(SimpleSourcePosition position, ParseNode argsNode) {
        this(position, argsNode, null);
    }
    
    public SuperParseNode(SimpleSourcePosition position, ParseNode argsNode, ParseNode iterNode) {
        super(position, argsNode != null && argsNode.containsVariableAssignment() ||
                iterNode != null && iterNode.containsVariableAssignment());
        this.argsNode = argsNode;
        this.iterNode = iterNode;
    }

    public NodeType getNodeType() {
        return NodeType.SUPERNODE;
    }

    /**
     * Accept for the visitor pattern.
     * @param iVisitor the visitor
     **/
    public <T> T accept(NodeVisitor<T> iVisitor) {
        return iVisitor.visitSuperNode(this);
    }

    /**
     * Gets the argsNode.
     * @return Returns a ParseNode
     */
    public ParseNode getArgsNode() {
        return argsNode;
    }
    
    public List<ParseNode> childNodes() {
        return iterNode != null ? createList(argsNode, iterNode) : createList(argsNode); 
    }

    public ParseNode getIterNode() {
        return iterNode;
    }

    public ParseNode setIterNode(ParseNode iterNode) {
        this.iterNode = iterNode;
        
        return this;
    }
}
