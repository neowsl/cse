import java.util.*;

/**
 * The {@link AssassinManager} class manages a game of Assassin. A game may be
 * started by constructing the object, and a method is provided to kill players.
 * Methods are also provided to print the kill ring and graveyard, and to
 * determine the winner of the game.
 *
 * @author Neal Wang <nealwang@uw.edu>
 * @assignment 3
 * @date 2025-10-17
 * @professor Stuart Reges
 * @ta XunMei Liu
 * @section BA
 */
public class AssassinManager {
	/**
	 * The head of a linked list that stores living {@link AssassinNode}s. Also
	 * stores the order of the kill ring (i.e. if {@code node1.next = node2}, then
	 * {@code node1} is stalking {@code node2}). The last node is stalking the first
	 * node.
	 */
	private AssassinNode killRingHead;
	/**
	 * The head of a linked list that stores dead {@link AssassinNode}s. Maintains
	 * reverse kill order (i.e. the most recently killed person will be the head of
	 * the list).
	 */
	private AssassinNode graveyardHead;

	/**
	 * Constructs a new {@link AssassinManager} using the names in {@code names}.
	 * Initializes the kill ring in the same order that names appear in
	 * {@code names} (i.e. if "Abby" appears immediately before "Bob" in
	 * {@code names}, then Abby is stalking Bob). The last person in {@code names}
	 * is stalking the first person in {@code names}.
	 * <p>
	 * Requires: All names must be nonempty and unique, ignoring case.
	 * <p>
	 * Ensures: {@code names} is not mutated.
	 *
	 * @param names the names of the people in the kill ring. Must not contain empty
	 *              strings or duplicates, ignoring case.
	 * @throws IllegalArgumentException if {@code names} is empty.
	 */
	public AssassinManager(List<String> names) {
		if (names.isEmpty()) {
			throw new IllegalArgumentException("names cannot be empty");
		}

		// start from the tail
		AssassinNode curr = new AssassinNode(names.get(names.size() - 1));

		// loop backwards since it's easier to add nodes at the head
		for (int i = names.size() - 2; i >= 0; i--) {
			curr = new AssassinNode(names.get(i), curr);
		}

		// initialize fields
		killRingHead = curr;
		graveyardHead = null;
	}

	/**
	 * Prints the names of the people in the kill ring to {@link System#out}, one
	 * per line, indented 4 spaces, in the form "A is stalking B". If only one
	 * person remains in the kill ring, the output will be "A is stalking A".
	 * <p>
	 * Requires: The kill ring contains at least one person.
	 * <p>
	 * Ensures: The names of the people in the kill ring are printed to
	 * {@link System#out}.
	 */
	public void printKillRing() {
		AssassinNode curr = killRingHead;
		while (curr.next != null) {
			System.out.println("    " + curr.name + " is stalking " + curr.next.name);
			curr = curr.next;
		}
		System.out.println("    " + curr.name + " is stalking " + killRingHead.name);
	}

	/**
	 * Prints the names of the people in the graveyard to {@link System#out}, one
	 * per line, indented 4 spaces, in the form "A was killed by B". Prints the
	 * names in reverse kill order (i.e. prints the most recently killed name
	 * first). Prints nothing if the graveyard is empty.
	 * <p>
	 * Requires: {@link AssassinManager#graveyardHead} is in reverse kill order.
	 * <p>
	 * Ensures: The names of the people in the graveyard are printed to
	 * {@link System#out} in reverse kill order.
	 */
	public void printGraveyard() {
		AssassinNode curr = graveyardHead;
		while (curr != null) {
			System.out.println("    " + curr.name + " was killed by " + curr.killer);
			curr = curr.next;
		}
	}

	/**
	 * Determines if the current kill ring contains the name {@code name}, ignoring
	 * case.
	 *
	 * @param name the name to check if the current kill ring contains, ignoring
	 *             case.
	 * @return {@code true} if the current kill ring contains the name {@code name},
	 *         {@code false} otherwise.
	 */
	public boolean killRingContains(String name) {
		return containsName(killRingHead, name);
	}

	/**
	 * Determines if the current graveyard contains the name {@code name}, ignoring
	 * case.
	 *
	 * @param name the name to check if the current graveyard contains, ignoring
	 *             case.
	 * @return {@code true} if the current graveyard contains the name {@code name},
	 *         {@code false} otherwise.
	 */
	public boolean graveyardContains(String name) {
		return containsName(graveyardHead, name);
	}

	/**
	 * Checks if the game is over (i.e. if the kill ring only contains one person).
	 * <p>
	 * Requires: The kill ring contains at least one person.
	 *
	 * @return {@code true} if the game is over, {@code false} otherwise.
	 */
	public boolean gameOver() {
		return killRingHead.next == null;
	}

	/**
	 * Gets the name of the winner of the game.
	 * <p>
	 * Requires: The kill ring contains at least one person.
	 *
	 * @return the name of the winner if the game is over, {@code null} otherwise.
	 */
	public String winner() {
		return gameOver() ? killRingHead.name : null;
	}

	/**
	 * Kills the person with name {@code name}, transferring the person from the
	 * kill ring to the graveyard.
	 * <p>
	 * Ensures:
	 * <ul>
	 * <li>The {@link AssassinNode#killer} of {@code name} is set to the
	 * killer.</li>
	 * <li>{@code name} is removed from the kill ring.</li>
	 * <li>{@code name} is added to the front of the graveyard.</li>
	 * <li>The order of the kill ring is preserved.
	 * </ul>
	 *
	 * @param name the name of the person to be killed.
	 * @throws IllegalStateException    if the game is already over.
	 * @throws IllegalArgumentException if {@code name} is not in the current kill
	 *                                  ring.
	 */
	public void kill(String name) {
		if (gameOver()) {
			throw new IllegalStateException("game is already over");
		}

		if (!killRingContains(name)) {
			throw new IllegalArgumentException("name is not in current kill ring");
		}

		AssassinNode prev = null;
		AssassinNode curr = killRingHead;

		// find the killed person `curr` and the killer `prev`
		while (curr != null && !curr.name.equalsIgnoreCase(name)) {
			prev = curr;
			curr = curr.next;
		}

		if (prev == null) {
			// handle case where killer is last node
			AssassinNode tail = findTail(killRingHead);
			curr.killer = tail.name;
			killRingHead = curr.next;
		} else {
			curr.killer = prev.name;
			prev.next = curr.next;
		}

		// push killed person to front of graveyard
		curr.next = graveyardHead;
		graveyardHead = curr;
	}

	/**
	 * Determines if a linked list of {@link AssassinNode}s with head {@code head}
	 * contains a node with {@link AssassinNode#name} equal to {@code name},
	 * ignoring case.
	 *
	 * @param head the head of the linked list.
	 * @param name the name to check if the linked list contains, ignoring case.
	 * @return {@code true} if the linked list contains the name {@code name},
	 *         {@code false} otherwise.
	 */
	private static boolean containsName(AssassinNode head, String name) {
		AssassinNode curr = head;
		while (curr != null) {
			if (curr.name.equalsIgnoreCase(name)) {
				return true;
			}
			curr = curr.next;
		}

		return false;
	}

	/**
	 * Finds the tail of a linked list of {@link AssassinNode}s with head
	 * {@code head}.
	 *
	 * @param head the head of the linked list.
	 * @return the tail of the linked list if its length is not 0, {@code null}
	 *         otherwise.
	 */
	private static AssassinNode findTail(AssassinNode head) {
		if (head == null) {
			return null;
		}

		AssassinNode curr = head;
		while (curr.next != null) {
			curr = curr.next;
		}

		return curr;
	}
}
