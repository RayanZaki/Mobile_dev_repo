import 'package:flutter_minesweaper/widgets/card.dart';

import 'items.dart';

class Game {
  static bool release = true;
  late String title = "Welcome to Memory Game";
  late List<MemoryCard> items;

  final Function setState;

  static bool started = false;

  int _turn = 0;
  Map<int, int> scores = <int, int>{};
  late int _numberOfPlayers;
  late List<String> _players;
  Game({required this.setState}) {}

  void nextTurn() {
    print(_turn);
    _turn = (_turn + 1) % _numberOfPlayers;
    title = "${_players[_turn]}'s turn";
    setState(() {});
  }

  void addScore(int score) {
    scores[_turn] = score;
  }

  void endGame() {
    started = false;
    title = "Game ended!";
    setState(() {});
  }

  void initGame({int? numberOfPlayers, List<String>? playerNames}) {
    _numberOfPlayers = numberOfPlayers ?? 2;
    _players = playerNames ??
        List.generate(_numberOfPlayers, (index) => "Player ${index + 1}");

    started = true;

    var i = 0;
    items = [];
    for (var item in generateItems()) {
      items.add(MemoryCard(item: item, onTap: play, id: i));
      i++;
    }

    scores = <int, int>{};
    title = "${_players[_turn]}'s turn";
  }

  MemoryCard? currentItem;
  static Function? prevSetState;

  int play({required MemoryCard card, required Function setState}) {
    if (!release || currentItem?.id == card.id) return 0;
    if (currentItem == null) {
      _handleFirstClick(card, setState);
      return -1;
    } else if (card.item == currentItem?.item) {
      _handleSuccess();
      return -1;
    } else {
      _handleFailure();
      release = false;
      return 500; // wait 500 ms
    }
  }

  _handleFirstClick(card, setState) {
    print("first one");
    currentItem = card;
    card.item.show();
    prevSetState = setState;
  }

  _handleSuccess() {
    print("success");

    currentItem = null;
    scores[_turn] = scores[_turn] != null ? scores[_turn]! + 1 : 1;
    _checkIfGameEnded();
  }

  _checkIfGameEnded() {
    print("Check if game ended");

    if (scores[_turn] == items.length / 4) {
      title = "${_players[_turn]} won";
      started = false;
      setState(() {});
    }
  }

  _handleFailure() {
    print("Failure");

    currentItem?.item.hide();
    currentItem = null;
    _checkIfGameEnded();
    nextTurn();
  }
}
