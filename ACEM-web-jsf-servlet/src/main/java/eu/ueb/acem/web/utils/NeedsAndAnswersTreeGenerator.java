package eu.ueb.acem.web.utils;

import java.util.Collection;
import java.util.Set;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.domain.beans.bleu.Reponse;
import eu.ueb.acem.services.NeedsAndAnswersService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.TreeNodeData;

@Controller("needsAndAnswersTreeGenerator")
@Scope("singleton")
public class NeedsAndAnswersTreeGenerator {

	private static final Logger logger = LoggerFactory.getLogger(NeedsAndAnswersTreeGenerator.class);
	
	private static final String TREE_NODE_TYPE_NEED_LEAF = "NeedLeaf";
	private static final String TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_NEEDS = "NeedWithAssociatedNeeds";
	private static final String TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS = "NeedWithAssociatedAnswers";
	private static final String TREE_NODE_TYPE_ANSWER_LEAF = "AnswerLeaf";

	@Autowired
	private NeedsAndAnswersService needsAndAnswersService;
	
	public String getTreeNodeType_NEED_LEAF() {
		return TREE_NODE_TYPE_NEED_LEAF;
	}

	public String getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS() {
		return TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_NEEDS;
	}

	public String getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS() {
		return TREE_NODE_TYPE_NEED_WITH_ASSOCIATED_ANSWERS;
	}

	public String getTreeNodeType_ANSWER_LEAF() {
		return TREE_NODE_TYPE_ANSWER_LEAF;
	}
	
	/**
	 * Returns a new {@link EditableTreeBean} containing the Pedagogical Advice
	 * nodes returned by the {@link NeedsAndAnswersService} implementation
	 * defined in this controller.
	 * 
	 * @param singleVisibleTreeRootLabel
	 *            is an optional string, that, if not null, will be the label of
	 *            a special unique node at the root of the tree. Even if the
	 *            data returned from the service have multiple roots, it can be
	 *            useful to force the creation of an ancestor node, for example
	 *            if the creation of a child node requires the user to
	 *            right-click on an existing node. That way, the user will be
	 *            able to start creating nodes even if there is no node returned
	 *            from the service.
	 */
	public EditableTreeBean createNeedAndAnswersTree(String singleVisibleTreeRootLabel) {
		EditableTreeBean treeBean = new EditableTreeBean();
		if (singleVisibleTreeRootLabel != null) {
			treeBean.addVisibleRoot(singleVisibleTreeRootLabel);
		}
		Collection<Besoin> needs = needsAndAnswersService.retrieveNeedsAtRoot();
		logger.info("Found {} needs at root of tree.", needs.size());
		for (Besoin need : needs) {
			logger.info("need = {}", need.getName());
			TreeNode currentVisibleRoot = null;
			if (singleVisibleTreeRootLabel != null) {
				// If the function was called with the
				// "singleVisibleTreeRootLabel" set,
				// we add the real roots of the tree as children of this
				// given "artificial" root.
				currentVisibleRoot = new DefaultTreeNode(getTreeNodeType_NEED_LEAF(), new TreeNodeData(need.getId(),
						need.getName(), "Need"), treeBean.getVisibleRoots().get(0));
				if (need.getChildren().size() > 0) {
					((DefaultTreeNode) currentVisibleRoot).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS());
				}
				else if (need.getAnswers().size() > 0) {
					((DefaultTreeNode) currentVisibleRoot).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS());
				}
			}
			else {
				// otherwise, we add the current Need as a visible root by
				// itself (thus allowing many visible roots, but it may lead
				// to an invisible tree if the service returns 0 node)
				currentVisibleRoot = treeBean.addVisibleRoot(need.getName());
			}
			for (Besoin child : need.getChildren()) {
				createChild(treeBean, child, currentVisibleRoot);
			}
		}
		if (singleVisibleTreeRootLabel != null) {
			treeBean.getVisibleRoots().get(0).setExpanded(true);
		}
		return treeBean;
	}

	/**
	 * Recursive function to construct Tree
	 */
	private void createChild(EditableTreeBean treeBean, Besoin need, TreeNode parentNode) {
		// We create the root node for this branch
		//TreeNode newNode = new DefaultTreeNode(getTreeNodeType_NEED_LEAF(), new TreeNodeData(need.getId(), need.getName(), "Need"), rootNode);
		TreeNode newNode = treeBean.addChild(getTreeNodeType_NEED_LEAF(), parentNode, need.getId(), need.getName(), "Need");
		// We look for children and recursively create them too
		@SuppressWarnings("unchecked")
		Collection<Besoin> associatedNeeds = (Collection<Besoin>) need.getChildren();
		if (associatedNeeds.size() > 0) {
			((DefaultTreeNode) newNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_NEEDS());
			for (Besoin besoinChild : associatedNeeds) {
				createChild(treeBean, besoinChild, newNode);
			}
		}

		@SuppressWarnings("unchecked")
		Collection<Reponse> answers = (Collection<Reponse>) need.getAnswers();
		if (answers.size() > 0) {
			((DefaultTreeNode) newNode).setType(getTreeNodeType_NEED_WITH_ASSOCIATED_ANSWERS());
			need.setAnswers((Set<Reponse>) answers);
			for (Reponse answer : answers) {
				//new DefaultTreeNode(getTreeNodeType_ANSWER_LEAF(), new TreeNodeData(answer.getId(), answer.getName(), "Answer"), newNode);
				treeBean.addChild(getTreeNodeType_ANSWER_LEAF(), newNode, answer.getId(), answer.getName(), "Answer");
			}
		}
	}

}
