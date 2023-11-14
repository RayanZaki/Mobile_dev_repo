import 'package:flutter/material.dart';

class MainMenu extends StatelessWidget {
  final TextEditingController numberController;
  const MainMenu({super.key, required this.numberController});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        children: [
          TextField(
            decoration: const InputDecoration(hintText: "How many players?"),
            keyboardType: TextInputType.number,
            controller: numberController,
          )
        ],
      ),
    );
  }
}
