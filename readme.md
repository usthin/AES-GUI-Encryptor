AES Encryption System

AES (Advanced Encryption Standard) is a symmetric block cipher used worldwide
for secure data encryption. AES uses a fixed block size of 128 bits and
supports key sizes of 128, 192, and 256 bits.

Number of rounds:
AES-128 → 10 rounds
AES-192 → 12 rounds
AES-256 → 14 rounds

Longer keys increase resistance to brute force attacks.

Modes of Operation

ECB (Electronic Codebook)
Each block is encrypted independently. It is simple but less secure
because identical plaintext blocks produce identical ciphertext.

CBC (Cipher Block Chaining)
Each plaintext block is XORed with the previous ciphertext block
before encryption. This improves security.

CFB (Cipher Feedback)
Transforms a block cipher into a stream cipher and allows encryption
of smaller units of data.

Key Handling

User keys are hashed using SHA-256 and trimmed to the required
length (128, 192, or 256 bits). This ensures the key always fits
the AES requirement.