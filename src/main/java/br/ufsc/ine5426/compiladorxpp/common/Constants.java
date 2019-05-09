package br.ufsc.ine5426.compiladorxpp.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

	public static final Symbol CFG_EPSILON = new Symbol('&');
	public static final Symbol CFG_EMPTY_STACK = new Symbol('$');

	/**
	 * Valor retirado do autômato desenvolvido pela equipe. Representa o estado
	 * especial de retração.
	 *
	 */
	public static final String RETRACT_STATE = "q27";
	/**
	 * Valor retirado do autômato desenvolvido pela equipe. Representa o estado
	 * especial de erro.
	 *
	 */
	public static final String ERROR_STATE = "q28";
	/**
	 * Valor retirado do autômato desenvolvido pela equipe. Representa o estado
	 * especial de erro para determinados caracteres.
	 *
	 */
	public static final String ERROR_STATE_CH_SPECIAL = "q29";

}
