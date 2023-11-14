import 'package:flutter/material.dart';
import 'package:flutter_minesweaper/utils/game.dart';
import 'package:flutter_minesweaper/widgets/menu.dart';

import 'widgets/grid.dart';

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  late Game game;
  final numberController = TextEditingController();
  @override
  void initState() {
    game = Game(setState: setState);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 0,
        backgroundColor: Colors.transparent,
        title: const Text(
          'Memory Game',
          style: TextStyle(color: Colors.blue),
        ),
      ),
      body: Column(
        children: [
          Text(
            game.title,
            style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 28),
          ),
          Expanded(
            child: Game.started
                ? Grid(game: game)
                : MainMenu(
                    numberController: numberController,
                  ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              ElevatedButton(
                onPressed: () {
                  setState(() {
                    game.initGame(
                        numberOfPlayers: numberController.text == ""
                            ? null
                            : int.parse(numberController.text));
                  });
                },
                child: Text(Game.started ? "Restart Game" : "Start Game"),
              ),
              if (Game.started)
                ElevatedButton(
                  onPressed: () {
                    game.endGame();
                  },
                  child: const Text("End Game"),
                )
            ],
          ),
        ],
      ),
    );
  }
}
