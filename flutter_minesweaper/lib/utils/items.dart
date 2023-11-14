import 'package:flutter/material.dart';
import 'package:flutter_minesweaper/utils/item.dart';

List<Item> generateItems() {
  List<Item> items = [
    Item(widget: const Icon(Icons.ac_unit), id: 1),
    Item(widget: const Icon(Icons.access_alarm), id: 2),
    Item(widget: const Icon(Icons.accessibility_outlined), id: 3),
    Item(widget: const Icon(Icons.account_balance), id: 4),
    Item(widget: const Icon(Icons.add), id: 5),
    Item(widget: const Icon(Icons.adb), id: 6),
    Item(widget: const Icon(Icons.back_hand_sharp), id: 7),
    Item(widget: const Icon(Icons.cabin_sharp), id: 8),
  ];
  var duplicated = items.toList();

  items.addAll(duplicated);
  items.shuffle();

  return items;
}
