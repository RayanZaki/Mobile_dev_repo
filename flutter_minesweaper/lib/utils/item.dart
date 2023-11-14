import 'package:flutter/material.dart';

class Item {
  final Widget widget;
  int id;
  bool isVisible = false;
  Item({required this.widget, required this.id});

  show() {
    isVisible = true;
  }

  hide() {
    isVisible = false;
  }
}
