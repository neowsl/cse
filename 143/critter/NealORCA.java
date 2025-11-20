import java.awt.*;
import java.util.*;

/**
 * @author Neal Wang <nealwang@uw.edu>
 * @date 2025-11-21
 * @professor Stuart Reges
 * @section AB
 */
public class NealORCA extends Critter {
	public static double[][] WEIGHTS = {
			{ -0.5221370675624564, 1.5233500922369765, -1.112900537243542, -0.6109008020405073, 0.02819569772071804,
					-0.036340443130743624, 0.1581489708518089, -0.15954598244099769, 1.3302843126351085,
					-0.44073754235312945, 1.056990037781826, -0.24626022060766944, 0.04851761705383402,
					0.47893064001386215, -0.1583992635549797, 0.6584601783448021, -0.24396519052319723,
					0.5812843499865021, 0.25235229415880656, -0.05437208750290365, -0.1243579705172174,
					0.15363728334041524, 0.16205263671561015, 0.6934927525234059 },
			{ 1.17071818677589, -0.6627708051992609, 0.04414752828851591, -0.9222960093244277, 0.39761370617283226,
					-0.030896249082691313, -0.307480818345394, 0.33995018670138355, 0.35134472484434875,
					0.7724766362984968, 0.5204714797261412, 0.31291464659366774, -0.4996749643749333,
					0.7963144818855928, 0.18738429718647914, 1.1900958293112258, 0.49724010308069644,
					-0.05443953574103181, -0.08539258241104697, -0.18628587369004634, -0.13802654460185998,
					0.6454994801199962, 1.1353125253672833, 1.0765410718158879 },
			{ -0.8494893261772308, 0.5274707009480386, -0.00942440725966779, 0.9711696357334131, -1.070156951079723,
					0.05028453366958051, 0.10362791181666128, -0.5286478538576629, 0.06193547948891573,
					0.6121810674180339, 0.28424942216883947, -0.5363014057654025, 1.375439137266654,
					-1.3028590310376074, -1.4378482403606947, -0.9461581464112955, 0.7235535880452757,
					-0.9062059030555528, -0.4313181419287956, -0.921930817385532, -0.6747112444109696,
					0.14401392915200853, 0.04496170909737929, -0.43809988113576936 },
			{ -0.19417065623241492, -1.142903070549063, -1.072333144645013, 2.8456119114496223, 1.2816802991707992,
					0.41890043774057384, 0.3797935752372847, 0.6324404093670346, 1.0053852086712671, 0.8911298580275552,
					0.6721147247019719, -0.6103387224715136, 0.15181320674714735, -0.8202574099625988,
					-0.2677434182126198, 0.2185282507514999, 0.2944324273829502, -1.0470637598606172,
					-0.6648921545163733, -0.24899575917551836, 0.3458744878114617, 0.34189705204693566,
					0.17742599072741871, 0.43099793637525485 }
	};

	private static final String[] katakana = {
			"ｦ", "ｧ", "ｨ", "ｩ", "ｪ", "ｫ", "ｬ", "ｭ", "ｮ", "ｯ",
			"ｰ", "ｱ", "ｲ", "ｳ", "ｴ", "ｵ", "ｶ", "ｷ", "ｸ", "ｹ", "ｺ",
			"ｻ", "ｼ", "ｽ", "ｾ", "ｿ", "ﾀ", "ﾁ", "ﾂ", "ﾃ", "ﾄ",
			"ﾅ", "ﾆ", "ﾇ", "ﾈ", "ﾉ", "ﾊ", "ﾋ", "ﾌ", "ﾍ", "ﾎ",
			"ﾏ", "ﾐ", "ﾑ", "ﾒ", "ﾓ", "ﾔ", "ﾕ", "ﾖ", "ﾗ", "ﾘ",
			"ﾙ", "ﾚ", "ﾛ", "ﾜ", "ﾝ", "ﾞ", "ﾟ"
	};

	private int visible = 0;

	public Color getColor() {
		Random r = new Random();
		if (r.nextInt(40) == 0) {
			visible = r.nextInt(10) + 5;
		}

		return visible > 0 ? Color.BLUE : Color.CYAN;
	}

	public String toString() {
		if (visible > 0) {
			visible--;
			return "λ";
		}

		Random r = new Random();
		return katakana[r.nextInt(katakana.length)];
	}

	public Action getMove(CritterInfo info) {
		double[] features = extractFeatures(info);
		int actionIndex = argmax(WEIGHTS, features);

		switch (actionIndex) {
			case 0:
				return Action.HOP;
			case 1:
				return Action.LEFT;
			case 2:
				return Action.RIGHT;
			case 3:
				return Action.INFECT;
			default:
				return Action.HOP;
		}
	}

	private double[] extractFeatures(CritterInfo info) {
		double[] v = new double[24];
		int i = 0;

		i = encodeNeighbor(info.getFront(), v, i);
		i = encodeNeighbor(info.getLeft(), v, i);
		i = encodeNeighbor(info.getRight(), v, i);
		i = encodeNeighbor(info.getBack(), v, i);

		v[i++] = info.frontThreat() ? 1 : 0;
		v[i++] = info.leftThreat() ? 1 : 0;
		v[i++] = info.rightThreat() ? 1 : 0;
		v[i++] = info.backThreat() ? 1 : 0;

		Direction d = info.getDirection();
		v[i + dirIndex(d)] = 1;

		return v;
	}

	private int encodeNeighbor(Neighbor n, double[] v, int offset) {
		switch (n) {
			case WALL:
				v[offset + 0] = 1;
				break;
			case EMPTY:
				v[offset + 1] = 1;
				break;
			case SAME:
				v[offset + 2] = 1;
				break;
			case OTHER:
				v[offset + 3] = 1;
				break;
		}
		return offset + 4;
	}

	private int dirIndex(Direction d) {
		switch (d) {
			case NORTH:
				return 0;
			case EAST:
				return 1;
			case SOUTH:
				return 2;
			case WEST:
				return 3;
			default:
				return 0;
		}
	}

	private int argmax(double[][] w, double[] x) {
		int res = 0;
		double best = dot(w[0], x);

		for (int i = 1; i < w.length; i++) {
			double score = dot(w[i], x);

			if (score > best) {
				best = score;
				res = i;
			}
		}

		return res;
	}

	private double dot(double[] a, double[] b) {
		double res = 0;

		for (int i = 0; i < a.length; i++) {
			res += a[i] * b[i];
		}

		return res;
	}
}
