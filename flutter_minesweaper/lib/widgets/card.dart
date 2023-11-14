import 'package:flutter/material.dart';

import '../utils/game.dart';
import '../utils/item.dart';

class MemoryCard extends StatefulWidget {
  final Item item;
  final int id;
  const MemoryCard(
      {super.key, required this.item, required this.onTap, required this.id});

  final int Function({required MemoryCard card, required Function setState})
      onTap;

  @override
  State<MemoryCard> createState() => _CardState();
}

class _CardState extends State<MemoryCard> {
  show() {
    widget.item.show();
    setState(() {});
  }

  hide() {
    widget.item.hide();

    if (Game.prevSetState != null) {
      Game.prevSetState!(() {});
      Game.prevSetState = null;
    }

    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () async {
        if (!Game.started) return;
        final showTimeDuration = widget.onTap(card: widget, setState: setState);
        if (showTimeDuration == 0) {
          return;
        }
        if (showTimeDuration == -1) {
          show();
        } else {
          show();
          await Future.delayed(Duration(milliseconds: showTimeDuration));
          hide();
        }
        Game.release = true;
      },
      child: !widget.item.isVisible
          ? Container(
              color: Colors.blue.withOpacity(0.3),
            )
          : widget.item.widget,
    );
  }
}
