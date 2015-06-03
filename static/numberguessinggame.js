var numberGuesser = (function() {
	
	var MIN_NUM = 0; 
	var MAX_NUM = 1000;		
	
	var minVal = 0;
	var maxVal = 1000;
	
	var currGuess = 0;
	
	var numberOfAttempts = 0;
	var isReady = false;
	
	var guess = {};
	
	/**
	 * Guesses the number with give minVal and maxVal and returns the
	 * guessed number
	 */
	function guessNextNumber(minVal, maxVal) {
		var guess = (minVal+maxVal)/2;
		
		guess = parseInt(guess, 10);
		
		currGuess = guess;
		
		numberOfAttempts++;
		
		return guess;
	}
	
	function clear() {
		guess.currGuess = 0;
		minVal = 0;
		maxVal = 1000;
		isReady = false;
		
	}
	
	function displayNextNumber(guess) {
		$("#guess").val(guess)
	}
	
	function displayMessage(message) {
		$("p#message").text(message);
	}
			
	guess.makeFirstGuess = function() {
		clear();
		minVal = MIN_NUM;
		maxVal = MAX_NUM;
			
		isReady = true;
		var guess = guessNextNumber(minVal, maxVal);
		
		displayNextNumber(guess);
	}
	
	guess.makeGuessForHigh = function() {
		
		if(isReady){
			minVal = currGuess;
			var guess = guessNextNumber(currGuess+1, maxVal);
			displayNextNumber(guess);
		}
	}
	
	guess.makeGuessForLow = function() {
		if(isReady) {
			maxVal = currGuess;
			var guess = guessNextNumber(minVal, currGuess-1);
			displayNextNumber(guess);
		}
	}
	
	guess.makeGuessForYes = function() {
		if(isReady) {
			clear();
			var message = "Thank you for playing. It took "+numberOfAttempts+" attempts to guess your number";
			displayMessage(message);
		}
	}
		
	return guess;
})();

$(document).ready(function(){
	
	$("#ready").click(function() {
		numberGuesser.makeFirstGuess();
	});
	
	$("#high").click(function() {
		numberGuesser.makeGuessForHigh();
	});
	
	$("#low").click(function() {
		numberGuesser.makeGuessForLow();
	});
	
	$("#yes").click(function() {
		numberGuesser.makeGuessForYes();
	});
});