import 'package:flutter/material.dart';

import '../utils/game.dart';

class Grid extends StatefulWidget {
  final Game game;
  const Grid({super.key, required this.game});

  @override
  State<Grid> createState() => _GridState();
}

class _GridState extends State<Grid> {
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(20.0),
      child: GridView.builder(
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 4, // number of items in each row
          mainAxisSpacing: 8.0, // spacing between rows
          crossAxisSpacing: 8.0, // spacing between columns
        ),
        itemCount: widget.game.items.length,
        itemBuilder: (context, index) {
          return widget.game.items[index];
        },
      ),
    );
  }
}
