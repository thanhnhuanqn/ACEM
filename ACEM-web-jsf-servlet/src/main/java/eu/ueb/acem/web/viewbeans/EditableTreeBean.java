/**
 *     Copyright Grégoire COLBERT 2015
 * 
 *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
 * 
 *     ACEM is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     ACEM is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with ACEM.  If not, see <http://www.gnu.org/licenses/>
 */
package eu.ueb.acem.web.viewbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Grégoire Colbert
 * @since 2013-11-20
 * 
 */
@Component("editableTreeBean")
@Scope("prototype")
public class EditableTreeBean implements Serializable {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(EditableTreeBean.class);

	private static final long serialVersionUID = -7640100553743316532L;

	private TreeNode root;

	private List<TreeNode> visibleRoots;

	private Map<Long, TreeNode> allAnswerLeaves;

	public EditableTreeBean() {
		// For some reason, the root of the tree is not visible
		root = new DefaultTreeNode(new TreeNodeData(null, "Root", null, null), null);

		// Therefore, we add a list of visible roots, so that it is possible to
		// right click on something to add children nodes
		visibleRoots = new ArrayList<TreeNode>();

		allAnswerLeaves = new HashMap<Long, TreeNode>();
	}

	public void clear() {
		root = new DefaultTreeNode(new TreeNodeData(null, "Root", null, null), null);
		visibleRoots = new ArrayList<TreeNode>();
	}

	public TreeNode getRoot() {
		return root;
	}

	public List<TreeNode> getVisibleRoots() {
		return visibleRoots;
	}

	public TreeNode addVisibleRoot(String label) {
		TreeNode node = new DefaultTreeNode(new TreeNodeData(null, label, null, null), root);
		visibleRoots.add(node);
		return node;
	}

	public void addVisibleRoot(TreeNode visibleRootNode) {
		root.getChildren().add(visibleRootNode);
	}

	/**
	 * 
	 * @param nodeType
	 *            The node type of the new node (supported types are defined by
	 *            the tree controller)
	 * @param parent
	 *            The parent of the new node
	 * @param id
	 *            The identifier of the underlying domain bean (so that we can,
	 *            for example, save the label if the user modifies it)
	 * @param label
	 *            The text displayed by the node
	 * @param concept
	 *            Allows different kinds of &lt;p:treeNode&gt; using the "type"
	 *            attribute (e.g. if "Answer" here, then the JSF can use
	 *            &lt;p:treeNode type="Answer"&gt;)
	 * @return the node that the method created
	 */
	public TreeNode addChild(String nodeType, TreeNode parent, Long id, String label, String concept) {
		TreeNode child = new DefaultTreeNode(nodeType, new TreeNodeData(id, label, concept, parent), parent);
		return child;
	}

	public void expandParentsOf(TreeNode node) {
		TreeNode parent = node.getParent();
		while (parent != null) {
			parent.setExpanded(true);
			parent = parent.getParent();
		}
	}

	public void expandIncludingChildren(TreeNode node) {
		node.setExpanded(true);
		for (TreeNode child : node.getChildren()) {
			child.setExpanded(true);
			if (!child.isLeaf()) {
				expandIncludingChildren(child);
			}
		}
	}

	public void collapseIncludingChildren(TreeNode node) {
		node.setExpanded(false);
		for (TreeNode child : node.getChildren()) {
			child.setExpanded(false);
			if (!child.isLeaf()) {
				collapseIncludingChildren(child);
			}
		}
	}

	public void collapseTree() {
		for (TreeNode visibleRoot : visibleRoots) {
			collapseIncludingChildren(visibleRoot);
		}
	}

	public void expandOnlyOneNode(TreeNode node) {
		if (node != null) {
			collapseTree();
			expandParentsOf(node);
			node.setExpanded(true);
		}
	}

	/**
	 * This method stores the given node in a Map for fast access of
	 * "pedagogical answer" leaves.
	 * 
	 * @param node
	 *            A TreeNode built upon a PedagogicalAnswer
	 */
	public void putAnswerLeaf(TreeNode node) {
		allAnswerLeaves.put(((TreeNodeData) node.getData()).getId(), node);
	}

	/**
	 * This method takes as parameter a set of node identifiers. It will modify
	 * this instance of EditableTreeBean so that only the given leaves and their
	 * parents remain. All nodes which are not in the given set, and which are
	 * not parents of the given nodes, will be deleted.
	 * 
	 * @param idsOfLeavesToKeep
	 *            The set of node identifiers to keep
	 */
	public void retainLeavesAndParents(Set<Long> idsOfLeavesToKeep) {
		Set<TreeNode> nodesToKeep = new HashSet<TreeNode>();
		for (Long id : idsOfLeavesToKeep) {
			TreeNode node = allAnswerLeaves.get(id);
			if (node != null) {
				nodesToKeep.add(node);
				while (node.getParent() != null) {
					node = node.getParent();
					nodesToKeep.add(node);
				}
			}
		}
		retainChildren(root, nodesToKeep);
	}

	/**
	 * This method recursively explores the given node and remove all children
	 * which are not in the given Set of TreeNodes to keep.
	 */
	private void retainChildren(TreeNode node, Set<TreeNode> nodesToKeep) {
		node.getChildren().retainAll(nodesToKeep);
		for (TreeNode child : node.getChildren()) {
			retainChildren(child, nodesToKeep);
		}
	}

	public static class TreeNodeData implements Serializable {

		private static final long serialVersionUID = -5623188924862380160L;

		private Long id = null;

		private String label;

		private String concept;

		private TreeNode parentBackup;

		public TreeNodeData(Long id, String label, String concept, TreeNode parent) {
			this.id = id;
			this.label = label;
			this.concept = concept;
			this.parentBackup = parent;
		}

		/**
		 * @return the identifier of the node, which is equal, by construction,
		 *         to the identifier of the underlying domain bean (so that we
		 *         can, for example, save the label if the user modifies it)
		 */
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getConcept() {
			return concept;
		}

		public void setConcept(String concept) {
			this.concept = concept;
		}

		/**
		 * This field is used to restore the original parent if a drag and drop
		 * of the current node is not allowed after the drop.
		 * 
		 * @return the parent of the node before the drag and drop.
		 */
		public TreeNode getParentBackup() {
			return parentBackup;
		}

		public void setParentBackup(TreeNode parent) {
			this.parentBackup = parent;
		}

		/**
		 * By convention, the CSS class returned by the TreeNodeData class will
		 * be equal to the "concept" parameter that was used to construct it.
		 * For example, if you create a node with the concept
		 * "PedagogicalAnswer", then you have to use a CSS class equal to
		 * ".PedagogicalAnswer" to give a style to this node.
		 * 
		 * @return the concept
		 */
		public String getStyleClass() {
			return getConcept();
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}

	}

}